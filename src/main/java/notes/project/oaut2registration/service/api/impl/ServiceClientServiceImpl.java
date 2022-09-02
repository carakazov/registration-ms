package notes.project.oaut2registration.service.api.impl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.api.ChangeServiceClientRolesRequestDto;
import notes.project.oaut2registration.dto.api.ChangeServiceClientRolesResponseDto;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationResponseDto;
import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.*;
import notes.project.oaut2registration.repository.ServiceClientRepository;
import notes.project.oaut2registration.service.api.*;
import notes.project.oaut2registration.service.integration.ServiceClientRegistrationProducer;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.mapper.ChangeServiceClientRolesResponseMapper;
import notes.project.oaut2registration.utils.mapper.ServiceClientHistoryMapper;
import notes.project.oaut2registration.utils.mapper.ServiceClientRegistrationMapper;
import notes.project.oaut2registration.utils.mapper.dto.ServiceClientHistoryMappingDto;
import notes.project.oaut2registration.utils.mapper.dto.ServiceClientRegistrationMappingDto;
import notes.project.oaut2registration.utils.uuid.UuidHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangeSystemClientRoleValidationDto;
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
    private final Validator<ChangeSystemClientRoleValidationDto> changeServiceClientRolesValidator;
    private final ChangeServiceClientRolesResponseMapper changeServiceClientRolesResponseMapper;


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
    public ChangeServiceClientRolesResponseDto changeServiceClientRole(ChangeServiceClientRolesRequestDto request, UUID clientExternalId) {
        String currentClientId = authHelper.getClientId();
        ServiceClient serviceClient = findByExternalId(clientExternalId);
        changeServiceClientRolesValidator.validate(
            new ChangeSystemClientRoleValidationDto(
                serviceClient.getRoles().stream().map(Role::getRoleTitle).collect(Collectors.toList()),
                request
            )
        );
        List<Role> roleToAdd = findAllRolesByClientIdAndRoleTitles(currentClientId, request.getRolesToAdd());
        List<Role> roleToRemove = findAllRolesByClientIdAndRoleTitles(currentClientId, request.getRolesToRemove());
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

    private List<Role> findAllRolesByClientIdAndRoleTitles(String clientId, List<String> roleTitles) {
        return roleTitles.stream()
            .map(item -> roleService.findByClientIdAndRoleTitle(clientId, item))
            .collect(Collectors.toList());
    }
}
