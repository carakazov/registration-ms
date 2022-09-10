package notes.project.oaut2registration.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.model.Authority;
import notes.project.oaut2registration.model.Scope;

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
    public static final String ANON_SCOPE = Scope.ANON.toString();
    public static final Boolean ANON_REGISTRATION_ENABLED = Boolean.TRUE;
    public static final Boolean USERNAME_ALREADY_EXISTS = Boolean.FALSE;
    public static final String AUTHORIZED_GRANT_TYPES = "password,refresh_token,client_credentials";
    public static final String OAUTH_CLIENT_AUTHORITY = Authority.OAUTH_CLIENT.toString();
    public static final String PASSWORD_ENCODED = "$2y$10$7nVqhnatX2u/eqekqv13ROjATEQIfkQr3ihtJahthID.WfJ6haeIu";

    public static final String ROLE_TITLE = "TEST_ROLE";
    public static final String RESERVED_ROLE = "OAUTH_CLIENT";

    public static final String USERNAME = "username";
    public static final String PLAIN_PASSWORD = "password";
    public static final String ENCODED_PASSWORD = "$2y$10$nmejUxXtnyIkGKFSuB8QheqUCcpB/De5gn0j8tfmghal.pvcAsD.a";

    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String MIDDLE_NAME = "middle name";
    public static final String EMAIL = "test@box.com";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(2000, 1, 1);
    public static final String ADDITIONAL_FIELD_NAME = "additional field name";
    public static final String ADDITIONAL_VALUE = "additional value";
    public static final String SERVICE_CLIENT_EXTERNAL_ID_STRING = "98fcee4c-452a-4d58-9960-ddd6e0eb47dc";
    public static final UUID SERVICE_CLIENT_EXTERNAL_ID = UUID.fromString(SERVICE_CLIENT_EXTERNAL_ID_STRING);
    public static final LocalDateTime REGISTRATION_DATE = LocalDateTime.of(2022, 8, 29, 10, 10, 10);
    public static final String CLIENT_ADDITIONAL_INFO_BINDING = "registered-clients-topic";

    public static final String ROLE_TO_REMOVE = "ROLE_TO_REMOVE";
    public static final String ROLE_TO_ADD = "ROLE_TO_ADD";
    public static final LocalDateTime EVENT_DATE = LocalDateTime.of(2022, 8, 10, 10, 10, 10);

    public static final String NEW_PASSWORD_PLAIN = "some-new-password";
    public static final String NEW_PASSWORD_ENCODED = "$2a$12$cx1VaaHbUBnDiYdhZMOQVe5KP3kQODsfloZuA3FcK4VGkPBJFY/5q";
    public static final String OPERATOR_SERVICE_CLIENT_EXTERNAL_ID_STRING = "ba1ccdc4-a199-45b4-b09e-c96cba4fd76d";
    public static final UUID OPERATOR_SERVICE_CLIENT_EXTERNAL_ID = UUID.fromString(OPERATOR_SERVICE_CLIENT_EXTERNAL_ID_STRING);

    public static final String RESTORE_CODE = "Kg5853C2cI77BdTVGyJGW53sQ";
    public static final String RESTORE_PASSWORD_TOPIC = "restore-password-topic";

    public static final String OPERATOR_CLIENT_ID = "operator";
    public static final Authority OAUTH_ADMIN_AUTHORITY = Authority.OAUTH_ADMIN;
    public static final LocalDateTime OAUTH_EVENT_DATE = LocalDateTime.of(2022, 10, 12, 10, 10, 10);
}
