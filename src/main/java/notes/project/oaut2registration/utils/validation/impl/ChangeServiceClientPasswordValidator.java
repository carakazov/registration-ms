package notes.project.oaut2registration.utils.validation.impl;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.utils.ValidationHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangePasswordValidationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeServiceClientPasswordValidator implements Validator<ChangePasswordValidationDto> {
    private final PasswordEncoder passwordEncoder;
    @Override
    public void validate(ChangePasswordValidationDto target) {

        ValidationHelper.getInstance()
            .validate(
                target,
                item -> !item.getRequest().getOldPassword().equals(item.getRequest().getNewPassword()),
                ExceptionCode.PASSWORDS_ARE_EQUAL
            ).validate(
                target,
                item -> passwordEncoder.matches(item.getRequest().getOldPassword(), item.getCurrentPassword()),
                ExceptionCode.INCORRECT_OLD_PASSWORD
            ).throwIfNotValid();
    }
}

