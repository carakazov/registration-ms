package notes.project.oaut2registration.utils.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.project.oaut2registration.config.ApplicationProperties;
import notes.project.oaut2registration.dto.ErrorDto;
import notes.project.oaut2registration.dto.ValidationErrorDto;
import notes.project.oaut2registration.exception.*;
import notes.project.oaut2registration.utils.ErrorHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ErrorHelperImpl implements ErrorHelper {
    private final ApplicationProperties properties;
    private static final String COMMON_EXCEPTION_CODE = "internalServerError";
    private static final String COMMON_EXCEPTION_MESSAGE = "Unexpected exception occurred during method execution";

    @Override
    public ValidationErrorDto from(ValidationException validationException) {
        List<ErrorDto> errors = new ArrayList<>();
        validationException.getCodes().forEach(item -> {
            String message = getMessageByCode(item);
            ErrorDto error = new ErrorDto();
            error.setMessage(message);
            error.setCode(item.getCode());
            errors.add(error);
        });
        logValidationException(validationException);
        return new ValidationErrorDto(errors);
    }

    @Override
    public ErrorDto from(Exception exception) {
        logException(exception);
        return new ErrorDto().setCode(COMMON_EXCEPTION_CODE).setMessage(COMMON_EXCEPTION_MESSAGE);
    }

    @Override
    public ErrorDto from(MethodArgumentNotValidException exception) {
        logException(exception);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(getMessageByCode(ExceptionCode.WRONG_REQUEST_PARAMETERS));
        errorDto.setCode(ExceptionCode.WRONG_REQUEST_PARAMETERS.getCode());
        return errorDto;
    }

    @Override
    public ErrorDto from(NotFoundException exception) {
        logException(exception);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(getMessageByCode(ExceptionCode.NOT_FOUND_EXCEPTION));
        errorDto.setCode(ExceptionCode.NOT_FOUND_EXCEPTION.getCode());
        return errorDto;
    }

    @Override
    public ErrorDto from(AnonRegistrationNotEnabledException exception) {
        logException(exception);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(ExceptionCode.ANON_REGISTRATION_NOT_ENABLED.getCode());
        errorDto.setMessage(getMessageByCode(ExceptionCode.ANON_REGISTRATION_NOT_ENABLED));
        return errorDto;
    }

    @Override
    public ErrorDto from(RegistrationSystemException exception) {
        logException(exception);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(getMessageByCode(exception.getCode()));
        errorDto.setCode(errorDto.getCode());
        return errorDto;
    }

    @Override
    public void from(SecurityContextException exception) {
        logException(exception);
    }

    @Override
    public void from(WrongRegistrationPasswordException exception) {
        logException(exception);
    }

    private String getMessageByCode(ExceptionCode code) {
        String exceptionMessage = properties.getMessage(code.getCode());
        if(StringUtils.isNotEmpty(exceptionMessage)) {
            return exceptionMessage;
        }
        return null;
    }

    private void logValidationException(ValidationException exception) {
        exception.getCodes().forEach(item -> log.error("Validation exception. Code: {}", item));
    }

    private void logException(Exception exception) {
        log.error("Exception occurred. Source message: {}", exception.getMessage());
    }

    private void logException(RuntimeException exception) {
        log.error("Exception occurred. Source message: {}", exception.getMessage());
    }
}
