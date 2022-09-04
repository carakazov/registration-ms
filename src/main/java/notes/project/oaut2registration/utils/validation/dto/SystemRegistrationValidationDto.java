package notes.project.oaut2registration.utils.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.dto.SystemRegistrationRequestDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SystemRegistrationValidationDto {
    private SystemRegistrationRequestDto request;
    private Boolean clientExists;
}
