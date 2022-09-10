package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.dto.ChangeAssignedResourcesRequestDto;
import notes.project.oaut2registration.dto.ChangeRoleScopesResponseDto;
import notes.project.oaut2registration.dto.CreateRoleRequestDto;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.Scope;

public interface RoleService {
    void createRole(CreateRoleRequestDto request);

    Role findByClientIdAndRoleTitle(String clientId, String roleTitle);

    void changeRoleStatus(String roleTitle);

    ChangeRoleScopesResponseDto changeScopes(ChangeAssignedResourcesRequestDto<Scope> request, String roleTitle);
}
