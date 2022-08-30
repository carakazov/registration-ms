package notes.project.oaut2registration.utils.validation.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ServiceClientRegistrationValidationDto {
    private String currentScope;
    private Boolean anonRegistrationEnabled;
    private Boolean usernameAlreadyExists;
    private LocalDate dateOfBirth;
}
