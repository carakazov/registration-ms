package notes.project.oaut2registration.utils.validation.impl;

import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.utils.ValidationHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.RestorePasswordValidationDto;
import org.springframework.stereotype.Component;

@Component
public class RestorePasswordValidator implements Validator<RestorePasswordValidationDto> {
    @Override
    public void validate(RestorePasswordValidationDto target) {
        ValidationHelper.getInstance()
            .validate(
                target,
                item -> item.getDetails().getClientId().equals(item.getServiceClient().getOauthClient().getClientId()),
                ExceptionCode.WRONG_SERVICE_CLIENT_EXTERNAL_ID
            ).validate(
                target.getInProcess(),
                Boolean.TRUE::equals,
                ExceptionCode.RESTORE_CODE_ALREADY_USED
            ).throwIfNotValid();
    }
}
