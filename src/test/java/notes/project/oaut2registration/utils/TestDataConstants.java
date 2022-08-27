package notes.project.oaut2registration.utils;

import io.swagger.models.auth.In;
import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.model.Authority;

@UtilityClass
public class TestDataConstants {
    //Common
    public static Long ID = 1L;
    public static Long ID_2 = 2L;
    public static final String EXCEPTION_CODE = "unexpectedErrorWhileCreationOperation";
    public static final String EXCEPTION_MESSAGE = "exception message";

    public static final String CLIENT_ID = "client id";
    public static final String CLIENT_SECRET = "client secret";
    public static final String REGISTRATION_PASSWORD = "registration password";
    public static final Integer ACCESS_TOKEN_VALIDITY = 20;
    public static final Integer REFRESH_TOKEN_VALIDITY = 30;

    public static final Integer MAX_ACCESS_TOKEN_VALIDITY = 100;
    public static final Integer MIN_ACCESS_TOKEN_VALIDITY = 10;
    public static final Integer MAX_REFRESH_TOKEN_VALIDITY = 100;
    public static final Integer MIN_REFRESH_TOKEN_VALIDITY = 10;

    public static final String SCOPE = "any";
    public static final String AUTHORIZED_GRANT_TYPES = "password,refresh_token,client_credentials";
    public static final String OAUTH_CLIENT_AUTHORITY = Authority.OAUTH_CLIENT.toString();
    public static final String PASSWORD_ENCODED = "$2y$10$7nVqhnatX2u/eqekqv13ROjATEQIfkQr3ihtJahthID.WfJ6haeIu";

    public static final String ROLE_TITLE = "TEST_ROLE";
    public static final String RESERVED_ROLE = "OAUTH_CLIENT";
}
