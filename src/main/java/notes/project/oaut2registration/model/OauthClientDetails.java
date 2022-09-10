package notes.project.oaut2registration.model;

import javax.persistence.Entity;
import javax.persistence.Id;

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

    private Boolean anonRegistrationEnabled;

    private Boolean blocked = Boolean.FALSE;
}
