package notes.project.oaut2registration.service.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import notes.project.oaut2registration.dto.ChangeServiceClientRolesResponseDto;
import notes.project.oaut2registration.dto.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.dto.ServiceClientRegistrationResponseDto;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.repository.ServiceClientRepository;
import notes.project.oaut2registration.service.api.impl.ServiceClientServiceImpl;
import notes.project.oaut2registration.service.integration.ServiceClientRegistrationProducer;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.mapper.ChangeServiceClientRolesResponseMapper;
import notes.project.oaut2registration.utils.mapper.ServiceClientHistoryMapper;
import notes.project.oaut2registration.utils.mapper.ServiceClientRegistrationMapper;
import notes.project.oaut2registration.utils.uuid.UuidHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangeSystemClientRoleValidationDto;
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
    private Validator<ChangeSystemClientRoleValidationDto> changeServiceClientRolesValidator;

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
            Mappers.getMapper(ChangeServiceClientRolesResponseMapper.class)
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
            ApiUtils.changeServiceClientRolesRequestDto(),
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
}
