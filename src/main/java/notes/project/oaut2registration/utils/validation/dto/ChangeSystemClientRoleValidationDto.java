package notes.project.oaut2registration.utils.validation.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.dto.api.ChangeServiceClientRolesRequestDto;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChangeSystemClientRoleValidationDto {
    private List<String> serviceClientRoles;
    private ChangeServiceClientRolesRequestDto request;
}
