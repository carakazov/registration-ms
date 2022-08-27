package notes.project.oaut2registration.utils.validation.impl;

import java.util.Collections;
import java.util.List;

import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.utils.ValidationHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.CreateRoleValidationDto;
import org.springframework.stereotype.Component;

@Component
public class CreateRoleValidator implements Validator<CreateRoleValidationDto> {
    private final List<String> RESERVED_ROLES = List.of(
        "OAUTH_CLIENT",
        "OAUTH_ADMIN"
    );

    @Override
    public void validate(CreateRoleValidationDto target) {
        ValidationHelper.getInstance()
            .validate(
                target.getRoleExists(),
                Boolean.FALSE::equals,
                ExceptionCode.ROLE_ALREADY_EXISTS
            ).validate(
                target.getRoleTitle(),
                item -> !RESERVED_ROLES.contains(item),
                ExceptionCode.RESERVED_ROLE_TITLE
            ).throwIfNotValid();
    }
}
