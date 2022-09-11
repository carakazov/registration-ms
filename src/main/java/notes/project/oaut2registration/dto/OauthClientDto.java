package notes.project.oaut2registration.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OauthClientDto {
    private String username;
    private Boolean blocked;
}
