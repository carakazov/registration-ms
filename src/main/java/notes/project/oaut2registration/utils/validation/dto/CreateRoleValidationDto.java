package notes.project.oaut2registration.utils.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleValidationDto {
    private String roleTitle;
    private Boolean roleExists;
}
