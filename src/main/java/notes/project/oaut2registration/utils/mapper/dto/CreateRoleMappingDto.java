package notes.project.oaut2registration.utils.mapper.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.SystemScope;

@Data
@AllArgsConstructor
public class CreateRoleMappingDto {
    private String roleTitle;
    private List<SystemScope> scopes;
    private OauthClientDetails details;
}
