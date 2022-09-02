package notes.project.oaut2registration.exception;

import lombok.Getter;

public enum ExceptionCode {
    INTERNAL_ERROR("internalError"),
    WRONG_REQUEST_PARAMETERS("wrongRequestParameters"),
    WRONG_ACCESS_TOKEN_VALIDITY("wrongAccessTokenValidity"),
    WRONG_REFRESH_TOKEN_VALIDITY("wrongRefreshTokenValidity"),
    REFRESH_VALIDITY_SHOULD_BE_MORE_THAN_ACCESS_VALIDITY("refreshValidityShouldBeMoreThanAccessValidity"),
    CLIENT_ALREADY_EXISTS("clientAlreadyExists"),
    NOT_FOUND_EXCEPTION("requestedResourceNotFound"),
    ROLE_ALREADY_EXISTS("roleAlreadyExists"),
    RESERVED_ROLE_TITLE("reservedRoleTitle"),
    ADDITIONAL_INFO_SENDING_EXCEPTION("additionalInfoSendingException"),
    ANON_REGISTRATION_NOT_ENABLED("anonRegistrationNotEnabled"),
    USERNAME_ALREADY_EXISTS("usernameAlreadyExists"),
    DATE_OF_BIRTH_IN_FUTURE("dateOfBirthInFuture"),
    SERVICE_CLIENT_NOT_FOUND("serviceClientNotFound"),
    SAME_ROLE_IN_BOTH_LISTS("sameRoleInBothLists"),
    USER_NOT_HAVE_ROLE("userNotHaveRole"),
    USER_ALREADY_HAS_ROLE("userAlreadyHasRole");
    @Getter
    private final String code;

    ExceptionCode(String code) {
        this.code = code;
    }
}
