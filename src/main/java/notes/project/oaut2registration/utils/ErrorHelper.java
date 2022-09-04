package notes.project.oaut2registration.utils;

import notes.project.oaut2registration.dto.ErrorDto;
import notes.project.oaut2registration.dto.ValidationErrorDto;
import notes.project.oaut2registration.exception.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ErrorHelper {
    ValidationErrorDto from(ValidationException validationException);
    ErrorDto from(Exception exception);
    ErrorDto from(MethodArgumentNotValidException exception);
    ErrorDto from(NotFoundException exception);
    ErrorDto from(RegistrationSystemException exception);
    ErrorDto from(AnonRegistrationNotEnabledException exception);

    void from(SecurityContextException exception);
    void from(WrongRegistrationPasswordException exception);
}
