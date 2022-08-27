package notes.project.oaut2registration.validation;

import io.swagger.annotations.Api;
import liquibase.pro.packaged.A;
import notes.project.oaut2registration.exception.ValidationException;
import notes.project.oaut2registration.exception.WrongRegistrationPasswordException;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.ApplicationPropertiesUtils;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.SystemRegistrationValidationDto;
import notes.project.oaut2registration.utils.validation.impl.RegisterSystemValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegisterSystemValidatorTest {
    private Validator<SystemRegistrationValidationDto> validator;

    @BeforeEach
    void init() {
        validator = new RegisterSystemValidator(ApplicationPropertiesUtils.applicationPropertiesForRegisterSystemValidator());
    }

    @Test
    void validateSuccess() {
        SystemRegistrationValidationDto validationDto = ApiUtils.systemRegistrationValidationDto();

        assertDoesNotThrow(() -> validator.validate(validationDto));
    }

    @Test
    void validateThrowWhenWrongRegistrationPassword() {
        SystemRegistrationValidationDto validationDto = ApiUtils.systemRegistrationValidationDto();

        validationDto.getRequest().setRegistrationPassword("NOT-CORRECT-TOKEN");

        assertThrows(
            WrongRegistrationPasswordException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenWrongAccessTokenValidity() {
        SystemRegistrationValidationDto validationDto = ApiUtils.systemRegistrationValidationDto();

        validationDto.getRequest().setAccessTokenValidity(1);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenWrongRefreshTokenValidity() {
        SystemRegistrationValidationDto validationDto = ApiUtils.systemRegistrationValidationDto();

        validationDto.getRequest().setRefreshTokenValidity(1);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenAccessTokenValidityMoreThenRefreshTokenValidity() {
        SystemRegistrationValidationDto validationDto = ApiUtils.systemRegistrationValidationDto();

        validationDto.getRequest().setAccessTokenValidity(validationDto.getRequest().getRefreshTokenValidity() + 10);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenClientExists() {
        SystemRegistrationValidationDto validationDto = ApiUtils.systemRegistrationValidationDto();

        validationDto.setClientExists(Boolean.TRUE);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }
}
