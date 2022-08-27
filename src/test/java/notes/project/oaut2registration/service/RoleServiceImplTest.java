package notes.project.oaut2registration.service;

import notes.project.oaut2registration.dto.CreateRoleRequestDto;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.repository.RoleRepository;
import notes.project.oaut2registration.service.impl.RoleServiceImpl;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.mapper.CreateRoleMapper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.CreateRoleValidationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static notes.project.oaut2registration.utils.TestDataConstants.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository repository;
    @Mock
    private OauthClientDetailsService oauthClientDetailsService;
    @Mock
    private SystemScopeService systemScopeService;
    @Mock
    private Validator<CreateRoleValidationDto> createRoleValidator;
    @Mock
    private AuthHelper authHelper;

    private RoleService service;

    @BeforeEach
    void init() {
        service = new RoleServiceImpl(
            repository,
            oauthClientDetailsService,
            systemScopeService,
            createRoleValidator,
            Mappers.getMapper(CreateRoleMapper.class),
            authHelper
        );
    }

    @Test
    void createRoleSuccess() {
        OauthClientDetails details = DbUtils.oauthClientDetails();
        CreateRoleRequestDto request = ApiUtils.createRoleRequestDto();

        when(authHelper.getClientId()).thenReturn(CLIENT_ID);
        when(oauthClientDetailsService.findByClientId(any())).thenReturn(details);
        when(repository.existsByDetailsAndRoleTitle(any(), any())).thenReturn(Boolean.FALSE);
        when(systemScopeService.findBySystemScope(any())).thenReturn(DbUtils.systemScope());
        when(repository.save(any())).thenReturn(DbUtils.role());

        service.createRole(request);

        verify(authHelper).getClientId();
        verify(oauthClientDetailsService).findByClientId(CLIENT_ID);
        verify(repository).existsByDetailsAndRoleTitle(details, ROLE_TITLE);
        verify(systemScopeService).findBySystemScope(Scope.ANON);
        verify(repository).save(DbUtils.role().setId(null));
    }
}
