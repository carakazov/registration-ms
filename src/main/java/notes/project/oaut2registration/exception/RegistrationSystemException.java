package notes.project.oaut2registration.exception;

import lombok.Getter;

public class RegistrationSystemException extends RuntimeException {
    @Getter
    private final ExceptionCode code;

    public RegistrationSystemException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }
}
