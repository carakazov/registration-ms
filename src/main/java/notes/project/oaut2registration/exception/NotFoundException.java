package notes.project.oaut2registration.exception;

import lombok.Getter;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
