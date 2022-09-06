package notes.project.oaut2registration.utils.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.ServiceClient;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestorePasswordValidationDto {
    private OauthClientDetails details;
    private ServiceClient serviceClient;
    private Boolean inProcess;
}
