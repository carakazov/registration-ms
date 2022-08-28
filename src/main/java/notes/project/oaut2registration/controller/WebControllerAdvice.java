package notes.project.oaut2registration.controller;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.api.ErrorDto;
import notes.project.oaut2registration.dto.api.ValidationErrorDto;
import notes.project.oaut2registration.exception.*;
import notes.project.oaut2registration.utils.ErrorHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class WebControllerAdvice {
    private final ErrorHelper errorHelper;


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleCommonException(Exception exception) {
        ErrorDto errorDto = errorHelper.from(exception);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleNotValidArgument(MethodArgumentNotValidException exception) {
        ErrorDto errorDto = errorHelper.from(exception);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorDto> handleValidationError(ValidationException exception) {
        ValidationErrorDto validationErrorDto = errorHelper.from(exception);
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongRegistrationPasswordException.class)
    public ResponseEntity<?> handleWrongRegistrationCodeException(WrongRegistrationPasswordException exception) {
        errorHelper.from(exception);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException exception) {
        ErrorDto errorDto = errorHelper.from(exception);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SecurityContextException.class)
    public ResponseEntity<?> handleSecurityContextException(SecurityContextException exception) {
        errorHelper.from(exception);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RegistrationSystemException.class)
    public ResponseEntity<ErrorDto> handleRegistrationSystemException(RegistrationSystemException exception) {
        ErrorDto errorDto = errorHelper.from(exception);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AnonRegistrationNotEnabledException.class)
    public ResponseEntity<ErrorDto> handleAnonRegistrationNotEnabled(AnonRegistrationNotEnabledException exception) {
        ErrorDto errorDto = errorHelper.from(exception);
        return new ResponseEntity<>(errorDto, HttpStatus.FORBIDDEN);
    }
}
