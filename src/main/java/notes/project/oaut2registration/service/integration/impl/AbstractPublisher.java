package notes.project.oaut2registration.service.integration.impl;

import lombok.extern.slf4j.Slf4j;
import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.exception.RegistrationSystemException;

@Slf4j
public abstract class AbstractPublisher {
    protected void sendNotFatalException(ExceptionCode code) {
        log.error("Not fatal exception while sending");
        throw new RegistrationSystemException(code, "Not fatal exception. Message is not sent");
    }

    protected void sendFatalException(ExceptionCode code, RuntimeException exception) {
        log.error("Fatal exception while sending");
        throw new RegistrationSystemException(code, exception.getMessage());
    }
}
