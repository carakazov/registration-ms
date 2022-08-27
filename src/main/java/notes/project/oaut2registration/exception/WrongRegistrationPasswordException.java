package notes.project.oaut2registration.exception;

public class WrongRegistrationPasswordException extends RuntimeException {
    public WrongRegistrationPasswordException(String message) {
        super(message);
    }
}
