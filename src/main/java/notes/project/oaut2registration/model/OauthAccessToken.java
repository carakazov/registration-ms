package notes.project.oaut2registration.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "oauth_access_token")
public class OauthAccessToken {
    @Id
    private String authenticationId;

    private String tokenId;

    private byte[] token;

    private String userName;

    private String clientId;

    private byte[] authentication;

    private String refreshToken;
}
