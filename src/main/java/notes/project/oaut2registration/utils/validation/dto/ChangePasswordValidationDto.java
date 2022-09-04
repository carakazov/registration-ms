package notes.project.oaut2registration.utils.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.dto.ChangePasswordRequestDto;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordValidationDto {
    private ChangePasswordRequestDto request;
    private String currentPassword;
}
