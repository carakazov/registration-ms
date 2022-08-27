package notes.project.oaut2registration.validation;

import io.swagger.annotations.Api;
import notes.project.oaut2registration.exception.ValidationException;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.CreateRoleValidationDto;
import notes.project.oaut2registration.utils.validation.impl.CreateRoleValidator;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static notes.project.oaut2registration.utils.TestDataConstants.*;

class CreateRoleValidatorTest {
    private Validator<CreateRoleValidationDto> validator;

    @BeforeEach
    void init() {
        validator = new CreateRoleValidator();
    }

    @Test
    void validateSuccess() {
        CreateRoleValidationDto validationDto = ApiUtils.createRoleValidationDto();

        assertDoesNotThrow(() -> validator.validate(validationDto));
    }

    @Test
    void validateThrowWhenRoleAlreadyExits() {
        CreateRoleValidationDto validationDto = ApiUtils.createRoleValidationDto();
        validationDto.setRoleExists(Boolean.TRUE);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenReservedRoleTitle() {
        CreateRoleValidationDto validationDto = ApiUtils.createRoleValidationDto();
        validationDto.setRoleTitle(RESERVED_ROLE);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }
}
