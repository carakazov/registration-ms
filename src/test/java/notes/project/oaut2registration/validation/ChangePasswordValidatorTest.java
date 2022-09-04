package notes.project.oaut2registration.validation;

import notes.project.oaut2registration.exception.ValidationException;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangePasswordValidationDto;
import notes.project.oaut2registration.utils.validation.impl.ChangeServiceClientPasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static notes.project.oaut2registration.utils.TestDataConstants.PLAIN_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ChangePasswordValidatorTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    private Validator<ChangePasswordValidationDto> validator;

    @BeforeEach
    void init() {
        validator = new ChangeServiceClientPasswordValidator(passwordEncoder);
    }

    @Test
    void validateSuccess() {
        ChangePasswordValidationDto validationDto = ApiUtils.changePasswordValidationDto();

        when(passwordEncoder.matches(any(), any())).thenReturn(Boolean.TRUE);

        assertDoesNotThrow(() -> validator.validate(validationDto));

        verify(passwordEncoder).matches(validationDto.getRequest().getOldPassword(), validationDto.getCurrentPassword());
    }

    @Test
    void validateThrowWhenPasswordsAreEqual() {
        ChangePasswordValidationDto validationDto = ApiUtils.changePasswordValidationDto();
        validationDto.getRequest().setNewPassword(PLAIN_PASSWORD);

        when(passwordEncoder.matches(any(), any())).thenReturn(Boolean.TRUE);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );

        verify(passwordEncoder).matches(validationDto.getRequest().getOldPassword(), validationDto.getCurrentPassword());
    }

    @Test
    void validateThrowWhenIncorrectPassword() {
        ChangePasswordValidationDto validationDto = ApiUtils.changePasswordValidationDto();

        when(passwordEncoder.matches(any(), any())).thenReturn(Boolean.FALSE);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(validationDto)
        );

        verify(passwordEncoder).matches(validationDto.getRequest().getOldPassword(), validationDto.getCurrentPassword());
    }
}
