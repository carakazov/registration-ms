package notes.project.oaut2registration.service.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import notes.project.oaut2registration.dto.*;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.*;
import notes.project.oaut2registration.repository.ServiceClientRepository;
import notes.project.oaut2registration.service.api.impl.ServiceClientServiceImpl;
import notes.project.oaut2registration.service.integration.RestorePasswordRequestProducer;
import notes.project.oaut2registration.service.integration.ServiceClientRegistrationProducer;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.code.RestoreCodeGenerator;
import notes.project.oaut2registration.utils.mapper.*;
import notes.project.oaut2registration.utils.uuid.UuidHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangeAssignedResourcesValidationDto;
import notes.project.oaut2registration.utils.validation.dto.ChangePasswordValidationDto;
import notes.project.oaut2registration.utils.validation.dto.RestorePasswordValidationDto;
import notes.project.oaut2registration.utils.validation.dto.ServiceClientRegistrationValidationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static notes.project.oaut2registration.utils.TestDataConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceClientServiceImplTest {
    @Mock
    private ServiceClientRepository repository;
    @Mock
    private OauthClientDetailsService oauthClientDetailsService;
    @Mock
    private RoleService roleService;
    @Mock
    private Validator<ServiceClientRegistrationValidationDto> serviceClientRegistrationValidator;
    @Mock
    private ServiceClientRegistrationProducer serviceClientRegistrationProducer;
    @Mock
    private AuthHelper authHelper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UuidHelper uuidHelper;
    @Mock
    private OauthAccessTokenService oauthAccessTokenService;
    @Mock
    private ServiceClientHistoryService serviceClientHistoryService;
    @Mock
    private Validator<ChangeAssignedResourcesValidationDto<String>> changeServiceClientRolesValidator;
    @Mock
    private Validator<ChangePasswordValidationDto> changePasswordValidator;
    @Mock
    private RestorePasswordRequestProducer restorePasswordRequestProducer;
    @Mock
    private RestoreCodeGenerator restoreCodeGenerator;
    @Mock
    private RestorePasswordStructService restorePasswordStructService;
    @Mock
    private Validator<RestorePasswordValidationDto> restorePasswordValidator;

    private ServiceClientService service;

    @BeforeEach
    void init() {
        service = new ServiceClientServiceImpl(
            repository,
            oauthClientDetailsService,
            roleService,
            serviceClientRegistrationValidator,
            Mappers.getMapper(ServiceClientRegistrationMapper.class),
            serviceClientRegistrationProducer,
            authHelper,
            passwordEncoder,
            uuidHelper,
            oauthAccessTokenService,
            serviceClientHistoryService,
            Mappers.getMapper(ServiceClientHistoryMapper.class),
            changeServiceClientRolesValidator,
            Mappers.getMapper(ChangeServiceClientRolesResponseMapper.class),
            changePasswordValidator,
            restorePasswordRequestProducer,
            Mappers.getMapper(RestorePasswordStructMapper.class),
            restoreCodeGenerator,
            restorePasswordStructService,
            restorePasswordValidator,
            Mappers.getMapper(ServiceListMapper.class)
        );
    }

    @Test
    void registerClientSuccess() {
        ServiceClientRegistrationRequestDto request = ApiUtils.serviceClientRegistrationRequestDto();
        OauthClientDetails details = DbUtils.oauthClientDetails();
        ServiceClient serviceClient = DbUtils.serviceClient();
        Role role = DbUtils.role();

        ServiceClientRegistrationResponseDto expected = ApiUtils.serviceClientRegistrationResponseDto();

        when(authHelper.getCurrentAuthority()).thenReturn(ANON_SCOPE);
        when(oauthClientDetailsService.findByClientId(any())).thenReturn(details);
        when(repository.existsByUsernameAndOauthClientClientId(any(), any())).thenReturn(USERNAME_ALREADY_EXISTS);
        when(passwordEncoder.encode(any())).thenReturn(ENCODED_PASSWORD);
        when(roleService.findByClientIdAndRoleTitle(any(), any())).thenReturn(role);
        when(repository.save(any())).thenReturn(serviceClient.setRegistrationDate(REGISTRATION_DATE));
        when(uuidHelper.generateUuid()).thenReturn(SERVICE_CLIENT_EXTERNAL_ID);

        ServiceClientRegistrationResponseDto actual = service.registerServiceClient(request);

        assertEquals(expected, actual);

        verify(authHelper).getCurrentAuthority();
        verify(oauthClientDetailsService).findByClientId(request.getAuthInformation().getClientId());
        verify(repository).existsByUsernameAndOauthClientClientId(
            request.getAuthInformation().getUsername(),
            request.getAuthInformation().getClientId()
        );
        verify(passwordEncoder).encode(request.getAuthInformation().getPassword());
        verify(roleService).findByClientIdAndRoleTitle(
            request.getAuthInformation().getClientId(),
            request.getAuthInformation().getServiceClientRoles().get(0)
        );
        verify(repository).save(serviceClient.setId(null).setRegistrationDate(null));
    }

    @Test
    void changeServiceClientRoleSuccess() {
        Role roleToAdd = DbUtils.role().setRoleTitle(ROLE_TO_ADD);
        Role roleToRemove = DbUtils.role().setRoleTitle(ROLE_TO_REMOVE);
        List<Role> roles = new ArrayList<>();
        roles.add(DbUtils.role().setRoleTitle(ROLE_TO_REMOVE));
        ServiceClient serviceClient = DbUtils.serviceClient().setRoles(roles);
        ChangeServiceClientRolesResponseDto expected = ApiUtils.changeServiceClientRolesResponseDto();

        when(authHelper.getClientId()).thenReturn(CLIENT_ID);
        when(authHelper.getCurrentUserName()).thenReturn(USERNAME);
        when(repository.findByExternalId(any())).thenReturn(Optional.of(serviceClient));
        when(roleService.findByClientIdAndRoleTitle(CLIENT_ID, ROLE_TO_REMOVE)).thenReturn(roleToRemove);
        when(roleService.findByClientIdAndRoleTitle(CLIENT_ID, ROLE_TO_ADD)).thenReturn(roleToAdd);
        when(repository.findByUsernameAndOauthClientClientId(any(), any())).thenReturn(serviceClient);

        ChangeServiceClientRolesResponseDto actual = service.changeServiceClientRole(
            ApiUtils.changeServiceClientRolesRequestDtoString(),
            SERVICE_CLIENT_EXTERNAL_ID
        );

        assertEquals(expected, actual);

        verify(repository).findByExternalId(SERVICE_CLIENT_EXTERNAL_ID);
        verify(roleService).findByClientIdAndRoleTitle(CLIENT_ID, ROLE_TO_REMOVE);
        verify(roleService).findByClientIdAndRoleTitle(CLIENT_ID, ROLE_TO_ADD);
        verify(repository).findByUsernameAndOauthClientClientId(USERNAME, CLIENT_ID);
    }

    @Test
    void findByExternalIdSuccess() {
        Optional<ServiceClient> expected = Optional.of(DbUtils.serviceClient());

        when(repository.findByExternalId(any())).thenReturn(expected);

        ServiceClient actual = service.findByExternalId(SERVICE_CLIENT_EXTERNAL_ID);

        assertEquals(expected.get(), actual);

        verify(repository).findByExternalId(SERVICE_CLIENT_EXTERNAL_ID);
    }

    @Test
    void findByExternalIdWhenNotFound() {
        Optional<ServiceClient> expected = Optional.empty();

        when(repository.findByExternalId(any())).thenReturn(expected);

        assertThrows(
            NotFoundException.class,
            () -> service.findByExternalId(SERVICE_CLIENT_EXTERNAL_ID)
        );

        verify(repository).findByExternalId(SERVICE_CLIENT_EXTERNAL_ID);
    }

    @Test
    void changePasswordSuccessWhenByUser() {
        ChangePasswordRequestDto request = ApiUtils.changePasswordRequestDto();
        request.setExternalId(null);

        ServiceClient serviceClient = DbUtils.serviceClient();
        ServiceClientHistory serviceClientHistory = DbUtils.serviceClientHistory();
        serviceClientHistory.setClient(serviceClient.setPassword(NEW_PASSWORD_ENCODED));
        serviceClientHistory.setOperator(serviceClient);
        serviceClientHistory.setEvent(HistoryEvent.PASSWORD_CHANGED);

        when(authHelper.getCurrentExternalId()).thenReturn(SERVICE_CLIENT_EXTERNAL_ID);
        when(repository.findByExternalId(any())).thenReturn(Optional.of(DbUtils.serviceClient()));
        when(passwordEncoder.encode(any())).thenReturn(NEW_PASSWORD_ENCODED);


        service.changePassword(request);

        verify(authHelper).getCurrentExternalId();
        verify(repository).findByExternalId(serviceClient.getExternalId());
        verify(passwordEncoder).encode(request.getNewPassword());
        verify(serviceClientHistoryService).save(serviceClientHistory.setId(null).setEventDate(null));
    }

    @Test
    void changePasswordSuccessWhenDifferentUser() {
        ChangePasswordRequestDto request = ApiUtils.changePasswordRequestDto();
        ServiceClientHistory serviceClientHistory = DbUtils.serviceClientHistory();
        serviceClientHistory.setEvent(HistoryEvent.PASSWORD_CHANGED);
        ServiceClient serviceClient = DbUtils.serviceClient();
        serviceClient.setPassword(NEW_PASSWORD_ENCODED);
        serviceClientHistory.setClient(serviceClient);
        ServiceClient operator = DbUtils.operator();
        operator.setExternalId(OPERATOR_SERVICE_CLIENT_EXTERNAL_ID);
        serviceClientHistory.setOperator(operator);

        when(authHelper.getCurrentExternalId()).thenReturn(OPERATOR_SERVICE_CLIENT_EXTERNAL_ID);
        when(repository.findByExternalId(SERVICE_CLIENT_EXTERNAL_ID)).thenReturn(Optional.of(serviceClient));
        when(repository.findByExternalId(OPERATOR_SERVICE_CLIENT_EXTERNAL_ID)).thenReturn(Optional.of(operator));
        when(passwordEncoder.encode(any())).thenReturn(NEW_PASSWORD_ENCODED);

        service.changePassword(request);

        verify(authHelper).getCurrentExternalId();
        verify(repository).findByExternalId(SERVICE_CLIENT_EXTERNAL_ID);
        verify(repository).findByExternalId(OPERATOR_SERVICE_CLIENT_EXTERNAL_ID);
        verify(passwordEncoder).encode(request.getNewPassword());
        verify(serviceClientHistoryService).save(serviceClientHistory.setId(null).setEventDate(null));
    }

    @Test
    void initializeRestorePasswordRequestSuccess() {
        OauthClientDetails details = DbUtils.oauthClientDetails();
        RestorePasswordStruct struct = DbUtils.restorePasswordStruct();
        InitializePasswordRestoreRequestDto request = ApiUtils.initializePasswordRestoreRequestDto();


        when(oauthClientDetailsService.findByClientId(any())).thenReturn(details);
        when(restoreCodeGenerator.generate()).thenReturn(RESTORE_CODE);
        when(passwordEncoder.encode(any())).thenReturn(NEW_PASSWORD_ENCODED);
        when(restorePasswordStructService.save(any())).thenReturn(struct);

        service.initializeRestorePasswordRequest(request);

        verify(oauthClientDetailsService).findByClientId(request.getClientId());
        verify(restoreCodeGenerator).generate();
        verify(passwordEncoder).encode(request.getNewPassword());
        verify(restorePasswordStructService).save(struct.setId(null));
        verify(restorePasswordRequestProducer).produceMessage(struct, request.getContact());
    }

    @Test
    void restorePasswordSuccess() {
        ServiceClient serviceClient = DbUtils.serviceClient();
        RestorePasswordStruct restorePasswordStruct = DbUtils.restorePasswordStruct();
        OauthClientDetails details = DbUtils.oauthClientDetails();

        when(repository.findByExternalId(any())).thenReturn(Optional.of(serviceClient));
        when(restorePasswordStructService.findByRestoreCode(any())).thenReturn(restorePasswordStruct);
        when(oauthClientDetailsService.findByClientId(any())).thenReturn(details);
        when(restorePasswordStructService.changeStructInProcessStatus(any())).thenReturn(restorePasswordStruct.setInProcess(Boolean.FALSE));

        service.restorePassword(SERVICE_CLIENT_EXTERNAL_ID, RESTORE_CODE);

        verify(repository).findByExternalId(SERVICE_CLIENT_EXTERNAL_ID);
        verify(restorePasswordStructService).findByRestoreCode(RESTORE_CODE);
        verify(oauthClientDetailsService).findByClientId(details.getClientId());
        verify(restorePasswordStructService).changeStructInProcessStatus(restorePasswordStruct);
    }

    @Test
    void changeUserStatusSuccess() {
        when(authHelper.getClientId()).thenReturn(CLIENT_ID);
        when(repository.findByExternalIdAndOauthClientClientId(SERVICE_CLIENT_EXTERNAL_ID, CLIENT_ID)).thenReturn(Optional.of(DbUtils.serviceClient()));
        when(authHelper.getCurrentExternalId()).thenReturn(OPERATOR_SERVICE_CLIENT_EXTERNAL_ID);
        when(repository.findByExternalId(any())).thenReturn(Optional.of(DbUtils.operator()));
        when(serviceClientHistoryService.save(any())).thenReturn(DbUtils.serviceClientHistory());

        service.changeUserStatus(SERVICE_CLIENT_EXTERNAL_ID);

        ServiceClientHistory history = DbUtils.serviceClientHistory();
        history.getClient().setBlocked(Boolean.TRUE);
        history.setId(null).setEventDate(null);

        verify(authHelper).getClientId();
        verify(repository).findByExternalIdAndOauthClientClientId(SERVICE_CLIENT_EXTERNAL_ID, CLIENT_ID);
        verify(authHelper).getCurrentExternalId();
        verify(repository).findByExternalId(OPERATOR_SERVICE_CLIENT_EXTERNAL_ID);
        verify(serviceClientHistoryService).save(history);
    }

    @Test
    void deleteTokenOfAllServiceClientOfRole() {
        when(authHelper.getClientId()).thenReturn(CLIENT_ID);
        when(roleService.findByClientIdAndRoleTitle(any(), any())).thenReturn(DbUtils.role());
        when(repository.findAllByRolesContaining(any())).thenReturn(Collections.singletonList(DbUtils.serviceClient()));

        service.deleteTokenOfAllServiceClientOfRole(ROLE_TITLE);

        verify(authHelper).getClientId();
        verify(roleService).findByClientIdAndRoleTitle(CLIENT_ID, ROLE_TITLE);
        verify(repository).findAllByRolesContaining(DbUtils.role());
        verify(oauthAccessTokenService).deleteAccessTokenByClientIdAndUserName(CLIENT_ID, USERNAME);
    }

    @Test
    void getServiceClientsListSuccess() {
        when(authHelper.getClientId()).thenReturn(CLIENT_ID);
        when(authHelper.getCurrentUserName()).thenReturn(OPERATOR_USERNAME);
        when(repository.findAllByOauthClientClientId(any())).thenReturn(Collections.singletonList(DbUtils.serviceClient()));

        ClientDtoListResponseDto<ServiceClientDto> expected = ApiUtils.serviceClientResponseDto();

        ClientDtoListResponseDto<ServiceClientDto> actual = service.getServiceClientsList();

        assertEquals(expected, actual);

        verify(authHelper).getClientId();
        verify(authHelper).getCurrentUserName();
        verify(repository).findAllByOauthClientClientId(CLIENT_ID);
    }
}
