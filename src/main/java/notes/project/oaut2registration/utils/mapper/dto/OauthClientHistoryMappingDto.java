package notes.project.oaut2registration.utils.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.OauthEvent;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OauthClientHistoryMappingDto {
    private OauthClientDetails oauthClient;
    private OauthClientDetails oauthAdmin;
    private OauthEvent oauthEvent;
}
