package notes.project.oaut2registration.service.api.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.ChangeAssignedResourcesRequestDto;
import notes.project.oaut2registration.dto.ChangeRoleScopesResponseDto;
import notes.project.oaut2registration.dto.CreateRoleRequestDto;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.SystemScope;
import notes.project.oaut2registration.repository.RoleRepository;
import notes.project.oaut2registration.service.api.OauthClientDetailsService;
import notes.project.oaut2registration.service.api.RoleService;
import notes.project.oaut2registration.service.api.SystemScopeService;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.mapper.ChangeRoleScopesMapper;
import notes.project.oaut2registration.utils.mapper.CreateRoleMapper;
import notes.project.oaut2registration.utils.mapper.dto.CreateRoleMappingDto;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangeAssignedResourcesValidationDto;
import notes.project.oaut2registration.utils.validation.dto.CreateRoleValidationDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final OauthClientDetailsService oauthClientDetailsService;
    private final SystemScopeService systemScopeService;
    private final Validator<CreateRoleValidationDto> createRoleValidator;
    private final CreateRoleMapper createRoleMapper;
    private final AuthHelper authHelper;
    private final Validator<ChangeAssignedResourcesValidationDto<Scope>> changeRoleScopesValidator;
    private final ChangeRoleScopesMapper changeRoleScopesMapper;

    @Override
    @Transactional
    public void createRole(CreateRoleRequestDto request) {
        String clientId = authHelper.getClientId();
        OauthClientDetails details = oauthClientDetailsService.findByClientId(clientId);
        createRoleValidator.validate(new CreateRoleValidationDto(
            request.getRoleTitle(),
            repository.existsByDetailsAndRoleTitle(details, request.getRoleTitle())
        ));
        List<SystemScope> scopes = request.getScopes().stream()
            .map(systemScopeService::findBySystemScope)
            .collect(Collectors.toList());
        repository.save(createRoleMapper.to(new CreateRoleMappingDto(request.getRoleTitle(), scopes, details)));
    }

    @Override
    public Role findByClientIdAndRoleTitle(String clientId, String roleTitle) {
        return repository.findByDetailsClientIdAndRoleTitle(clientId, roleTitle)
            .orElseThrow(() -> new NotFoundException("Role with title " + roleTitle + " not found in system " + clientId));
    }

    @Override
    @Transactional
    public void changeRoleStatus(String roleTitle) {
        Role role = findByClientIdAndRoleTitle(authHelper.getClientId(), roleTitle);
        role.setBlocked(!role.getBlocked());
    }

    @Override
    @Transactional
    public ChangeRoleScopesResponseDto changeScopes(ChangeAssignedResourcesRequestDto<Scope> request, String roleTitle) {
        String clientId = authHelper.getClientId();
        Role role = findByClientIdAndRoleTitle(clientId, roleTitle);
        changeRoleScopesValidator.validate(
            new ChangeAssignedResourcesValidationDto<>(
                role.getScopes().stream().map(SystemScope::getSystemScope).collect(Collectors.toList()),
                request
            )
        );
        List<SystemScope> scopesToRemove = request.getToRemove().stream().map(systemScopeService::findBySystemScope).collect(Collectors.toList());
        List<SystemScope> scopesToAdd = request.getToAdd().stream().map(systemScopeService::findBySystemScope).collect(Collectors.toList());
        role.getScopes().removeAll(scopesToRemove);
        role.getScopes().addAll(scopesToAdd);
        return changeRoleScopesMapper.to(role);
    }
}
