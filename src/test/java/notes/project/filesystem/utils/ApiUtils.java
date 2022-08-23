package notes.project.filesystem.utils;

import java.util.Collections;

import lombok.experimental.UtilityClass;
import notes.project.filesystem.dto.*;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.model.EventType;

import static notes.project.filesystem.utils.TestDataConstants.*;

@UtilityClass
public class ApiUtils {
    public static ErrorDto errorDto() {
        return new ErrorDto()
                .setCode(EXCEPTION_CODE)
                .setMessage(EXCEPTION_MESSAGE);
    }

    public static ValidationErrorDto validationErrorDto() {
        return new ValidationErrorDto(Collections.singletonList(errorDto()));
    }

    public static ValidationException validationException() {
        ValidationException validationException = new ValidationException();
        validationException.addCode(ExceptionCode.INTERNAL_ERROR);
        return validationException;
    }
}
