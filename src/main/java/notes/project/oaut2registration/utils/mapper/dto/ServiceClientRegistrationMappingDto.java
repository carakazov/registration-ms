package notes.project.oaut2registration.utils.mapper.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.Role;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ServiceClientRegistrationMappingDto {
    private String username;
    private String password;
    private UUID externalId;
    private OauthClientDetails oauthClient;
    private List<Role> roles;
}
