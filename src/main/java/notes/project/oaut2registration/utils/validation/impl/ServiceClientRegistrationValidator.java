package notes.project.oaut2registration.utils.validation.impl;

import java.time.LocalDate;

import notes.project.oaut2registration.exception.AnonRegistrationNotEnabledException;
import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.utils.ValidationHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ServiceClientRegistrationValidationDto;
import org.springframework.stereotype.Component;

@Component
public class ServiceClientRegistrationValidator implements Validator<ServiceClientRegistrationValidationDto> {
    @Override
    public void validate(ServiceClientRegistrationValidationDto target) {
        if(Scope.ANON.toString().equals(target.getCurrentScope()) && Boolean.FALSE.equals(target.getAnonRegistrationEnabled())) {
            throw new AnonRegistrationNotEnabledException("Anon registration not enabled");
        }

        ValidationHelper.getInstance()
            .validate(
                target.getUsernameAlreadyExists(),
                Boolean.FALSE::equals,
                ExceptionCode.USERNAME_ALREADY_EXISTS
            ).validate(
                target.getDateOfBirth(),
                LocalDate.now()::isAfter,
                ExceptionCode.DATE_OF_BIRTH_IN_FUTURE
            ).throwIfNotValid();
    }
}
