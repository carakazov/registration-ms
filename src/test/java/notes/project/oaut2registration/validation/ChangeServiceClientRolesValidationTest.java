package notes.project.oaut2registration.validation;

import java.util.Collections;

import notes.project.oaut2registration.exception.ValidationException;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangeAssignedResourcesValidationDto;
import notes.project.oaut2registration.utils.validation.impl.ChangeAssignedResourcesValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static notes.project.oaut2registration.utils.TestDataConstants.ROLE_TO_ADD;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChangeServiceClientRolesValidationTest {
    private Validator<ChangeAssignedResourcesValidationDto> validator;

    @BeforeEach
    void init() {
        validator = new ChangeAssignedResourcesValidator();
    }

    @Test
    void validateSuccess() {
        ChangeAssignedResourcesValidationDto validationDto = ApiUtils.changeSystemClientRoleValidationDtoString();

        assertDoesNotThrow(() -> validator.validate(validationDto));
    }

    @Test
    void validateThrowWhenSameRoleInBothLists() {
        ChangeAssignedResourcesValidationDto validationDto = ApiUtils.changeSystemClientRoleValidationDtoString();
        validationDto.getRequest().setToRemove(Collections.singletonList(ROLE_TO_ADD));

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenUserNotHaveRole() {
        ChangeAssignedResourcesValidationDto validationDto = ApiUtils.changeSystemClientRoleValidationDtoString();
        validationDto.setExistingList(Collections.singletonList(ROLE_TO_ADD));

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenUserAlreadyHasRole() {
        ChangeAssignedResourcesValidationDto validationDto = ApiUtils.changeSystemClientRoleValidationDtoString();
        validationDto.setExistingList(Collections.singletonList(ROLE_TO_ADD));

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }
}
