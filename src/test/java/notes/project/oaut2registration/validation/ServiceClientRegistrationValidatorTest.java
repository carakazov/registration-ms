package notes.project.oaut2registration.validation;

import java.time.LocalDate;

import notes.project.oaut2registration.exception.AnonRegistrationNotEnabledException;
import notes.project.oaut2registration.exception.ValidationException;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ServiceClientRegistrationValidationDto;
import notes.project.oaut2registration.utils.validation.impl.ServiceClientRegistrationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceClientRegistrationValidatorTest {
    private Validator<ServiceClientRegistrationValidationDto> validator;

    @BeforeEach
    void init() {
        validator = new ServiceClientRegistrationValidator();
    }

    @Test
    void validateSuccess() {
        ServiceClientRegistrationValidationDto validationDto = ApiUtils.serviceClientRegistrationValidationDto();

        assertDoesNotThrow(
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenAnonRegistrationNotEnabled() {
        ServiceClientRegistrationValidationDto validationDto = ApiUtils.serviceClientRegistrationValidationDto();
        validationDto.setAnonRegistrationEnabled(Boolean.FALSE);

        assertThrows(
            AnonRegistrationNotEnabledException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenUsernameAlreadyExists() {
        ServiceClientRegistrationValidationDto validationDto = ApiUtils.serviceClientRegistrationValidationDto();
        validationDto.setUsernameAlreadyExists(Boolean.TRUE);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }

    @Test
    void validateThrowWhenDateOfBirthInTheFuture() {
        ServiceClientRegistrationValidationDto validationDto = ApiUtils.serviceClientRegistrationValidationDto();
        validationDto.setDateOfBirth(LocalDate.now().plusDays(1));

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );
    }
}
