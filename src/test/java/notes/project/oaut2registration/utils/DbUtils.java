package notes.project.oaut2registration.utils;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.dto.SystemRegistrationRequestDto;
import notes.project.oaut2registration.model.OauthClientDetails;

import static notes.project.oaut2registration.utils.TestDataConstants.*;

@UtilityClass
public class DbUtils {
    public static OauthClientDetails oauthClientDetails() {
        return new OauthClientDetails()
            .setClientId(CLIENT_ID)
            .setClientSecret(PASSWORD_ENCODED)
            .setScope(SCOPE)
            .setAuthorities(OAUTH_CLIENT_AUTHORITY)
            .setAuthorizedGrantTypes(AUTHORIZED_GRANT_TYPES)
            .setAccessTokenValidity(ACCESS_TOKEN_VALIDITY)
            .setRefreshTokenValidity(REFRESH_TOKEN_VALIDITY);
    }
}
