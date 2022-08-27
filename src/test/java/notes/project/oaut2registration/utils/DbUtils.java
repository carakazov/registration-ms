package notes.project.oaut2registration.utils;

import java.util.Collections;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.dto.SystemRegistrationRequestDto;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.SystemScope;

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
