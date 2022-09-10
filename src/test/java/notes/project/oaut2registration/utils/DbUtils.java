package notes.project.oaut2registration.utils;

import java.util.Collections;
import java.util.List;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.model.*;

import static notes.project.oaut2registration.utils.TestDataConstants.*;

@UtilityClass
public class DbUtils {
    public static OauthClientHistory oauthClientHistory() {
        return new OauthClientHistory()
            .setId(ID)
            .setOauthClient(oauthClientDetails())
            .setOauthAdmin(oauthClientDetailsOperator())
            .setOauthEvent(OauthEvent.BLOCKED)
            .setEventDate(OUATH_EVENT_DATE);
    }

    public static OauthClientDetails oauthClientDetailsOperator() {
        return oauthClientDetails().setClientId(OPERATOR_CLIENT_ID).setAuthorities(OAUTH_ADMIN_AUTHORITY.toString());
    }

    public static RestorePasswordStruct restorePasswordStruct() {
        return new RestorePasswordStruct()
            .setId(ID)
            .setDetails(oauthClientDetails())
            .setNewPassword(NEW_PASSWORD_ENCODED)
            .setRestoreCode(RESTORE_CODE)
            .setInProcess(Boolean.TRUE);
    }

    public static ServiceClientHistory serviceClientHistory() {
        return new ServiceClientHistory()
            .setId(ID)
            .setClient(serviceClient())
            .setEventDate(EVENT_DATE)
            .setEvent(HistoryEvent.BLOCKED)
            .setOperator(operator());
    }

    public static ServiceClient operator() {
        return serviceClient().setId(ID_2).setExternalId(OPERATOR_SERVICE_CLIENT_EXTERNAL_ID);
    }

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

    public static SystemScope systemScope(Scope scope) {
        return new SystemScope()
            .setId(ID)
            .setSystemScope(scope);
    }

    public static Role role() {
        return new Role()
            .setId(ID)
            .setRoleTitle(ROLE_TITLE)
            .setDetails(oauthClientDetails())
            .setScopes(List.of(systemScope()));
    }
}
