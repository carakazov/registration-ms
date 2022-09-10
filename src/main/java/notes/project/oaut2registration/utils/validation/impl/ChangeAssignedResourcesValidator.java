package notes.project.oaut2registration.utils.validation.impl;


import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.utils.ValidationHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ChangeAssignedResourcesValidationDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Component
public class ChangeAssignedResourcesValidator<T> implements Validator<ChangeAssignedResourcesValidationDto<T>> {
    @Override
    public void validate(ChangeAssignedResourcesValidationDto<T> target) {
        ValidationHelper validationHelper = ValidationHelper.getInstance();

        if(!target.getRequest().getToAdd().isEmpty() && !target.getRequest().getToRemove().isEmpty()) {
            validationHelper
                .validate(
                    target,
                    item -> Boolean.FALSE.equals(CollectionUtils.containsAny(target.getRequest().getToRemove(), target.getRequest().getToAdd())),
                    ExceptionCode.SAME_ROLE_IN_BOTH_LISTS
                );
        }
        validationHelper
            .validate(
                target,
                item -> Boolean.TRUE.equals(target.getExistingList().containsAll(target.getRequest().getToRemove())),
                ExceptionCode.NOT_HAVE_RESOURCE
            )
            .validate(
                target,
                item -> Boolean.FALSE.equals(CollectionUtils.containsAny(target.getExistingList(), target.getRequest().getToAdd())),
                ExceptionCode.ALREADY_HAS_RESOURCE
            ).throwIfNotValid();
    }
}
