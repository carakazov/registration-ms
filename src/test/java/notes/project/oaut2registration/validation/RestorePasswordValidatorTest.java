package notes.project.oaut2registration.validation;


import notes.project.oaut2registration.exception.ValidationException;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.RestorePasswordValidationDto;
import notes.project.oaut2registration.utils.validation.impl.RestorePasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestorePasswordValidatorTest {
    private Validator<RestorePasswordValidationDto> validator;

    @BeforeEach
    void init() {
        validator = new RestorePasswordValidator();
    }

    @Test
    void validateSuccess() {
        RestorePasswordValidationDto validationDto = ApiUtils.restorePasswordValidationDto();

        assertDoesNotThrow(() -> validator.validate(validationDto));
    }

    @Test
    void validateThrowWhenWrongClientExternalId() {
        RestorePasswordValidationDto validationDto = ApiUtils.restorePasswordValidationDto();
        validationDto.getDetails().setClientId("not correct id");

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenRestoreCodeAlreadyUsed() {
        RestorePasswordValidationDto validationDto = ApiUtils.restorePasswordValidationDto();
        validationDto.setInProcess(Boolean.FALSE);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }
}
