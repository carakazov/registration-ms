package notes.project.oaut2registration.utils;

import java.util.Collections;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.dto.*;
import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.exception.ValidationException;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.utils.validation.dto.CreateRoleValidationDto;
import notes.project.oaut2registration.utils.validation.dto.SystemRegistrationValidationDto;

import static notes.project.oaut2registration.utils.TestDataConstants.*;

@UtilityClass
public class ApiUtils {

    public static CreateRoleRequestDto createRoleRequestDto() {
        return new CreateRoleRequestDto()
            .setRoleTitle(ROLE_TITLE)
            .setScopes(Collections.singletonList(Scope.ANON));
    }

    public static CreateRoleValidationDto createRoleValidationDto() {
        return new CreateRoleValidationDto()
            .setRoleExists(Boolean.FALSE)
            .setRoleTitle(ROLE_TITLE);
    }

    public static SystemRegistrationValidationDto systemRegistrationValidationDto() {
        return new SystemRegistrationValidationDto()
            .setRequest(systemRegistrationRequestDto())
            .setClientExists(Boolean.FALSE);
    }

    public static SystemRegistrationRequestDto systemRegistrationRequestDto() {
        return new SystemRegistrationRequestDto()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .setRegistrationPassword(REGISTRATION_PASSWORD)
            .setAccessTokenValidity(ACCESS_TOKEN_VALIDITY)
            .setRefreshTokenValidity(REFRESH_TOKEN_VALIDITY);
    }

    public static ErrorDto errorDto() {
        return new ErrorDto()
                .setCode(EXCEPTION_CODE)
                .setMessage(EXCEPTION_MESSAGE);
    }

    public static ValidationErrorDto validationErrorDto() {
        return new ValidationErrorDto(Collections.singletonList(errorDto()));
    }

    public static ValidationException validationException() {
        ValidationException validationException = new ValidationException();
        validationException.addCode(ExceptionCode.INTERNAL_ERROR);
        return validationException;
    }
}
