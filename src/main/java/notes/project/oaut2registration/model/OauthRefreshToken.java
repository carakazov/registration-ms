package notes.project.oaut2registration.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "oauth_refresh_token")
public class OauthRefreshToken {
    @Id
    private String tokenId;

    private byte[] token;

    private byte[] authentication;
}
