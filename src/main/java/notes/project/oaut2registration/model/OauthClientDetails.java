package notes.project.oaut2registration.model;

import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "oauth_client_details")
public class OauthClientDetails {
    @Id
    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private Boolean autoapprove;
}
