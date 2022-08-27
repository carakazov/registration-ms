package notes.project.oaut2registration.utils.validation.impl;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.config.ApplicationProperties;
import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.exception.WrongRegistrationPasswordException;
import notes.project.oaut2registration.utils.ValidationHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.SystemRegistrationValidationDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterSystemValidator implements Validator<SystemRegistrationValidationDto> {
    private final ApplicationProperties properties;

    @Override
    public void validate(SystemRegistrationValidationDto target) {
        if(!properties.getSystemRegistrationKeyWords().contains(target.getRequest().getRegistrationPassword())) {
            throw new WrongRegistrationPasswordException("Wrong secret code.");
        }

        ValidationHelper.getInstance()
            .validate(
                target.getRequest().getAccessTokenValidity(),
                item -> item >= properties.getMinAccessTokenValidity() && item <= properties.getMaxAccessTokenValidity(),
                ExceptionCode.WRONG_ACCESS_TOKEN_VALIDITY
            ).validate(
                target.getRequest().getRefreshTokenValidity(),
                item -> item >= properties.getMinRefreshTokenValidity() && item <= properties.getMaxRefreshTokenValidity(),
                ExceptionCode.WRONG_REFRESH_TOKEN_VALIDITY
            ).validate(
                target,
                item -> target.getRequest().getRefreshTokenValidity() > target.getRequest().getAccessTokenValidity(),
                ExceptionCode.REFRESH_VALIDITY_SHOULD_BE_MORE_THAN_ACCESS_VALIDITY
            ).validate(
                target.getClientExists(),
                Boolean.FALSE::equals,
                ExceptionCode.CLIENT_ALREADY_EXISTS
            ).throwIfNotValid();
    }
}
