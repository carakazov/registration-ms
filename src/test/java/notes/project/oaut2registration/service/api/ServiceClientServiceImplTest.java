package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.dto.api.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationResponseDto;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.repository.ServiceClientRepository;
import notes.project.oaut2registration.service.api.impl.ServiceClientServiceImpl;
import notes.project.oaut2registration.service.integration.ServiceClientRegistrationProducer;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.mapper.ServiceClientRegistrationMapper;
import notes.project.oaut2registration.utils.uuid.UuidHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ServiceClientRegistrationValidationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static notes.project.oaut2registration.utils.TestDataConstants.*;

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
            uuidHelper
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
}
