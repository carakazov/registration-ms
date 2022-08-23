package notes.project.filesystem.utils;

import notes.project.filesystem.dto.ErrorDto;
import notes.project.filesystem.dto.ValidationErrorDto;
import notes.project.filesystem.exception.ValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ErrorHelper {
    ValidationErrorDto from(ValidationException validationException);
    ErrorDto from(Exception exception);
    ErrorDto from(MethodArgumentNotValidException exception);
}
