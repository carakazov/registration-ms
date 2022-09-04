package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.dto.CreateRoleRequestDto;
import notes.project.oaut2registration.model.Role;

public interface RoleService {
    void createRole(CreateRoleRequestDto request);

    Role findByClientIdAndRoleTitle(String clientId, String roleTitle);
}
