package notes.project.oaut2registration.utils;

import java.util.Collections;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.model.*;

import static notes.project.oaut2registration.utils.TestDataConstants.*;

@UtilityClass
public class DbUtils {
    public static ServiceClient serviceClient() {
        return new ServiceClient()
            .setId(ID)
            .setExternalId(SERVICE_CLIENT_EXTERNAL_ID)
            .setUsername(USERNAME)
            .setPassword(ENCODED_PASSWORD)
            .setRegistrationDate(REGISTRATION_DATE)
            .setOauthClient(oauthClientDetails())
            .setBlocked(Boolean.FALSE)
            .setRoles(Collections.singletonList(role()));
    }

    public static OauthClientDetails oauthClientDetails() {
        return new OauthClientDetails()
            .setClientId(CLIENT_ID)
            .setClientSecret(PASSWORD_ENCODED)
            .setScope(SCOPE)
            .setAuthorities(OAUTH_CLIENT_AUTHORITY)
            .setAuthorizedGrantTypes(AUTHORIZED_GRANT_TYPES)
            .setAccessTokenValidity(ACCESS_TOKEN_VALIDITY)
            .setRefreshTokenValidity(REFRESH_TOKEN_VALIDITY)
            .setAnonRegistrationEnabled(Boolean.TRUE);
    }

    public static SystemScope systemScope() {
        return new SystemScope()
            .setId(ID)
            .setSystemScope(Scope.ANON);
    }

    public static Role role() {
        return new Role()
            .setId(ID)
            .setRoleTitle(ROLE_TITLE)
            .setDetails(oauthClientDetails())
            .setScopes(Collections.singletonList(systemScope()));
    }
}
