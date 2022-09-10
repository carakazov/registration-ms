package notes.project.oaut2registration.service.api.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.*;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.*;
import notes.project.oaut2registration.repository.ServiceClientRepository;
import notes.project.oaut2registration.service.api.*;
import notes.project.oaut2registration.service.integration.RestorePasswordRequestProducer;
import notes.project.oaut2registration.service.integration.ServiceClientRegistrationProducer;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.code.RestoreCodeGenerator;
import notes.project.oaut2registration.utils.mapper.ChangeServiceClientRolesResponseMapper;
import notes.project.oaut2registration.utils.mapper.RestorePasswordStructMapper;
import notes.project.oaut2registration.utils.mapper.ServiceClientHistoryMapper;
import notes.project.oaut2registration.utils.mapper.ServiceClientRegistrationMapper;
import notes.project.oaut2registration.utils.mapper.dto.RestorePasswordStructMappingDto;
import notes.project.oaut2registration.utils.mapper.dto.ServiceClientHistoryMappingDto;
import notes.project.oaut2registration.utils.mapper.dto.ServiceClientRegistrationMappingDto;
import notes.project.oaut2registration.utils.uuid.UuidHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangeAssignedResourcesValidationDto;
import notes.project.oaut2registration.utils.validation.dto.ChangePasswordValidationDto;
import notes.project.oaut2registration.utils.validation.dto.RestorePasswordValidationDto;
import notes.project.oaut2registration.utils.validation.dto.ServiceClientRegistrationValidationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceClientServiceImpl implements ServiceClientService {
    private final ServiceClientRepository repository;
    private final OauthClientDetailsService oauthClientDetailsService;
    private final RoleService roleService;
    private final Validator<ServiceClientRegistrationValidationDto> serviceClientRegistrationValidator;
    private final ServiceClientRegistrationMapper serviceClientRegistrationMapper;
    private final ServiceClientRegistrationProducer serviceClientRegistrationProducer;
    private final AuthHelper authHelper;
    private final PasswordEncoder passwordEncoder;
    private final UuidHelper uuidHelper;
    private final OauthAccessTokenService oauthAccessTokenService;
    private final ServiceClientHistoryService serviceClientHistoryService;
    private final ServiceClientHistoryMapper serviceClientHistoryMapper;
    private final Validator<ChangeAssignedResourcesValidationDto<String>> changeServiceClientRolesValidator;
    private final ChangeServiceClientRolesResponseMapper changeServiceClientRolesResponseMapper;
    private final Validator<ChangePasswordValidationDto> changePasswordValidator;
    private final RestorePasswordRequestProducer restorePasswordRequestProducer;
    private final RestorePasswordStructMapper restorePasswordStructMapper;
    private final RestoreCodeGenerator restoreCodeGenerator;
    private final RestorePasswordStructService restorePasswordStructService;
    private final Validator<RestorePasswordValidationDto> restorePasswordValidator;


    @Override
    @Transactional
    public ServiceClientRegistrationResponseDto registerServiceClient(ServiceClientRegistrationRequestDto request) {
        String currentScope = authHelper.getCurrentAuthority();
        OauthClientDetails details = oauthClientDetailsService.findByClientId(request.getAuthInformation().getClientId());
        serviceClientRegistrationValidator.validate(
            new ServiceClientRegistrationValidationDto(
                currentScope,
                details.getAnonRegistrationEnabled(),
                repository.existsByUsernameAndOauthClientClientId(request.getAuthInformation().getUsername(), details.getClientId()),
                request.getAdditionalInformation().getDateOfBirth()
            )
        );
        ServiceClient serviceClient = serviceClientRegistrationMapper.to(
            new ServiceClientRegistrationMappingDto(
                request.getAuthInformation().getUsername(),
                passwordEncoder.encode(request.getAuthInformation().getPassword()),
                uuidHelper.generateUuid(),
                details,
                request.getAuthInformation().getServiceClientRoles().stream()
                    .map(item -> roleService.findByClientIdAndRoleTitle(
                        request.getAuthInformation().getClientId(),
                        item
                    )).collect(Collectors.toList())
            )
        );

        serviceClient = repository.save(serviceClient);

        serviceClientRegistrationProducer.produceMessage(request, serviceClient);

        return serviceClientRegistrationMapper.from(request.getAuthInformation(), serviceClient);
    }

    @Override
    public ServiceClient findByExternalId(UUID externalId) {
        return repository.findByExternalId(externalId)
            .orElseThrow(() -> new NotFoundException("Service client " + externalId + " not found"));
    }

    @Override
    @Transactional
    public ChangeServiceClientRolesResponseDto changeServiceClientRole(ChangeAssignedResourcesRequestDto<String> request, UUID clientExternalId) {
        String currentClientId = authHelper.getClientId();
        ServiceClient serviceClient = findByExternalId(clientExternalId);
        changeServiceClientRolesValidator.validate(
            new ChangeAssignedResourcesValidationDto<>(
                serviceClient.getRoles().stream().map(Role::getRoleTitle).collect(Collectors.toList()),
                request
            )
        );
        List<Role> roleToAdd = findAllRolesByClientIdAndRoleTitles(currentClientId, request.getToAdd());
        List<Role> roleToRemove = findAllRolesByClientIdAndRoleTitles(currentClientId, request.getToRemove());
        serviceClient.getRoles().removeAll(roleToRemove);
        serviceClient.getRoles().addAll(roleToAdd);
        ServiceClientHistory serviceClientHistory = serviceClientHistoryMapper.to(
            new ServiceClientHistoryMappingDto(
                serviceClient,
                repository.findByUsernameAndOauthClientClientId(authHelper.getCurrentUserName(), currentClientId),
                HistoryEvent.ROLE_LIST_CHANGED
            )
        );
        serviceClientHistoryService.save(serviceClientHistory);
        oauthAccessTokenService.deleteAccessTokenByClientIdAndUserName(currentClientId, serviceClient.getUsername());
        return changeServiceClientRolesResponseMapper.to(serviceClient);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequestDto request) {
        UUID operatorExternalId = authHelper.getCurrentExternalId();
        ServiceClient operator = findByExternalId(operatorExternalId);
        ServiceClientHistory serviceClientHistory;
        if(Objects.nonNull(request.getExternalId())) {
            ServiceClient clientToChange = findByExternalId(request.getExternalId());
            changePassword(clientToChange, request);
            serviceClientHistory = serviceClientHistoryMapper.to(new ServiceClientHistoryMappingDto(
                clientToChange,
                operator,
                HistoryEvent.PASSWORD_CHANGED
            ));
        } else {
            changePassword(operator, request);
            serviceClientHistory = serviceClientHistoryMapper.to(new ServiceClientHistoryMappingDto(
                operator,
                operator,
                HistoryEvent.PASSWORD_CHANGED
            ));
        }
        serviceClientHistoryService.save(serviceClientHistory);
    }

    @Override
    @Transactional
    public void initializeRestorePasswordRequest(InitializePasswordRestoreRequestDto request) {
        OauthClientDetails details = oauthClientDetailsService.findByClientId(request.getClientId());
        RestorePasswordStruct struct = restorePasswordStructMapper.to(
            new RestorePasswordStructMappingDto(
                details,
                restoreCodeGenerator.generate(),
                passwordEncoder.encode(request.getNewPassword())
            )
        );
        struct = restorePasswordStructService.save(struct);
        restorePasswordRequestProducer.produceMessage(struct, request.getContact());
    }

    @Override
    @Transactional
    public void restorePassword(UUID clientExternalId, String restoreCode) {
        ServiceClient serviceClient = findByExternalId(clientExternalId);
        RestorePasswordStruct struct = restorePasswordStructService.findByRestoreCode(restoreCode);
        restorePasswordValidator.validate(
            new RestorePasswordValidationDto(
                oauthClientDetailsService.findByClientId(struct.getDetails().getClientId()),
                serviceClient,
                struct.getInProcess()
            )
        );
        serviceClient.setPassword(struct.getNewPassword());
        restorePasswordStructService.changeStructInProcessStatus(struct);
    }

    @Override
    @Transactional
    public void changeUserStatus(UUID externalId) {
        String clientId = authHelper.getClientId();
        UUID operatorExternalId = authHelper.getCurrentExternalId();
        ServiceClient serviceClient = repository.findByExternalIdAndOauthClientClientId(externalId, clientId)
            .orElseThrow(() -> new NotFoundException("Service client " + externalId + " does not exist in system " + clientId));
        serviceClient.setBlocked(!serviceClient.getBlocked());
        ServiceClient operator = findByExternalId(operatorExternalId);
        serviceClientHistoryService.save(serviceClientHistoryMapper.to(
            new ServiceClientHistoryMappingDto(
                serviceClient,
                operator,
                Boolean.TRUE.equals(serviceClient.getBlocked()) ? HistoryEvent.BLOCKED : HistoryEvent.UNBLOCK
            )
        ));
    }

    @Override
    @Transactional
    public void deleteTokenOfAllServiceClientOfRole(String roleTitle) {
        String clientId = authHelper.getClientId();
        Role role = roleService.findByClientIdAndRoleTitle(clientId, roleTitle);
        List<ServiceClient> clients = repository.findAllByRolesContaining(role);
        clients.forEach(item -> oauthAccessTokenService.deleteAccessTokenByClientIdAndUserName(clientId, item.getUsername()));
    }

    private void changePassword(ServiceClient serviceClient, ChangePasswordRequestDto request) {
        changePasswordValidator.validate(new ChangePasswordValidationDto(request, serviceClient.getPassword()));
        serviceClient.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    private List<Role> findAllRolesByClientIdAndRoleTitles(String clientId, List<String> roleTitles) {
        return roleTitles.stream()
            .map(item -> roleService.findByClientIdAndRoleTitle(clientId, item))
            .collect(Collectors.toList());
    }
}
