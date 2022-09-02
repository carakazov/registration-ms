package notes.project.oaut2registration.validation;

import java.util.Collections;

import notes.project.oaut2registration.exception.ValidationException;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangeSystemClientRoleValidationDto;
import notes.project.oaut2registration.utils.validation.impl.ChangeServiceClientRolesValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static notes.project.oaut2registration.utils.TestDataConstants.*;

class ChangeServiceClientRolesValidationTest {
    private Validator<ChangeSystemClientRoleValidationDto> validator;

    @BeforeEach
    void init() {
        validator = new ChangeServiceClientRolesValidator();
    }

    @Test
    void validateSuccess() {
        ChangeSystemClientRoleValidationDto validationDto = ApiUtils.changeSystemClientRoleValidationDto();

        assertDoesNotThrow(() -> validator.validate(validationDto));
    }

    @Test
    void validateThrowWhenSameRoleInBothLists() {
        ChangeSystemClientRoleValidationDto validationDto = ApiUtils.changeSystemClientRoleValidationDto();
        validationDto.getRequest().setRolesToRemove(Collections.singletonList(ROLE_TO_ADD));

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenUserNotHaveRole() {
        ChangeSystemClientRoleValidationDto validationDto = ApiUtils.changeSystemClientRoleValidationDto();
        validationDto.setServiceClientRoles(Collections.singletonList(ROLE_TO_ADD));

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenUserAlreadyHasRole() {
        ChangeSystemClientRoleValidationDto validationDto = ApiUtils.changeSystemClientRoleValidationDto();
        validationDto.setServiceClientRoles(Collections.singletonList(ROLE_TO_ADD));

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }
}
