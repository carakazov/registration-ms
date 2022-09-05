package notes.project.oaut2registration.utils.mapper.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.OauthClientDetails;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestorePasswordStructMappingDto {
    private OauthClientDetails details;
    private String restoreCode;
    private String newPassword;
}
