package notes.project.oaut2registration.service.api;

import java.util.Optional;

import notes.project.oaut2registration.dto.CreateRoleRequestDto;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.repository.RoleRepository;
import notes.project.oaut2registration.service.api.impl.RoleServiceImpl;
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

import static notes.project.oaut2registration.utils.TestDataConstants.CLIENT_ID;
import static notes.project.oaut2registration.utils.TestDataConstants.ROLE_TITLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void findByClientIdAndRoleTitleSuccess() {
        Optional<Role> expected = Optional.of(DbUtils.role());

        when(repository.findByDetailsClientIdAndRoleTitle(any(), any())).thenReturn(expected);

        Role actual = service.findByClientIdAndRoleTitle(CLIENT_ID, ROLE_TITLE);

        assertEquals(expected.get(), actual);

        verify(repository).findByDetailsClientIdAndRoleTitle(CLIENT_ID, ROLE_TITLE);
    }

    @Test
    void findByClientIdAndRoleTitleWhenNotFound() {
        Optional<Role> expected = Optional.empty();

        when(repository.findByDetailsClientIdAndRoleTitle(any(), any())).thenReturn(expected);

        assertThrows(
            NotFoundException.class,
            () -> service.findByClientIdAndRoleTitle(CLIENT_ID, ROLE_TITLE)
        );

        verify(repository).findByDetailsClientIdAndRoleTitle(CLIENT_ID, ROLE_TITLE);
    }

    @Test
    void changeRoleStatusSuccess() {
        Role role = DbUtils.role();
        when(authHelper.getClientId()).thenReturn(CLIENT_ID);
        when(repository.findByDetailsClientIdAndRoleTitle(any(), any())).thenReturn(Optional.of(role));
        assertDoesNotThrow(() -> service.changeRoleStatus(role.getRoleTitle()));

        verify(repository).findByDetailsClientIdAndRoleTitle(CLIENT_ID, role.getRoleTitle());
        verify(authHelper).getClientId();
    }


    @Test
    void changeRoleStatusWhen() {
        Optional<Role> role = Optional.empty();
        when(authHelper.getClientId()).thenReturn(CLIENT_ID);
        when(repository.findByDetailsClientIdAndRoleTitle(any(), any())).thenReturn(role);

        assertThrows(
            NotFoundException.class,
            () -> service.changeRoleStatus(ROLE_TITLE)
        );

        verify(authHelper).getClientId();
        verify(repository).findByDetailsClientIdAndRoleTitle(CLIENT_ID, ROLE_TITLE);
    }
}
