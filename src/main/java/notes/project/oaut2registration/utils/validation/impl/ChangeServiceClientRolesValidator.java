package notes.project.oaut2registration.utils.validation.impl;


import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.utils.ValidationHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangeSystemClientRoleValidationDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Component
public class ChangeServiceClientRolesValidator implements Validator<ChangeSystemClientRoleValidationDto> {
    @Override
    public void validate(ChangeSystemClientRoleValidationDto target) {
        ValidationHelper validationHelper = ValidationHelper.getInstance();

        if(!target.getRequest().getRolesToAdd().isEmpty() && !target.getRequest().getRolesToRemove().isEmpty()) {
            ValidationHelper.getInstance()
                .validate(
                    target,
                    item -> Boolean.FALSE.equals(CollectionUtils.containsAny(target.getRequest().getRolesToRemove(), target.getRequest().getRolesToAdd())),
                    ExceptionCode.SAME_ROLE_IN_BOTH_LISTS
                );
        }
        validationHelper
            .validate(
                target,
                item -> Boolean.TRUE.equals(target.getServiceClientRoles().containsAll(target.getRequest().getRolesToRemove())),
                ExceptionCode.USER_NOT_HAVE_ROLE
            )
            .validate(
                target,
                item -> Boolean.FALSE.equals(CollectionUtils.containsAny(target.getServiceClientRoles(), target.getRequest().getRolesToAdd())),
                ExceptionCode.USER_ALREADY_HAS_ROLE
            ).throwIfNotValid();
    }
}
