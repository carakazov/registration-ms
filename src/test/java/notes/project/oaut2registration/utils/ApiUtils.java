package notes.project.oaut2registration.utils;

import java.util.Collections;

import javax.print.attribute.standard.MediaSize;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.dto.api.*;
import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.exception.ValidationException;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.utils.validation.dto.ChangeSystemClientRoleValidationDto;
import notes.project.oaut2registration.utils.validation.dto.CreateRoleValidationDto;
import notes.project.oaut2registration.utils.validation.dto.ServiceClientRegistrationValidationDto;
import notes.project.oaut2registration.utils.validation.dto.SystemRegistrationValidationDto;

import static notes.project.oaut2registration.utils.TestDataConstants.*;

@UtilityClass
public class ApiUtils {

    public static ChangeServiceClientRolesResponseDto changeServiceClientRolesResponseDto() {
        return new ChangeServiceClientRolesResponseDto()
            .setUserExternalId(SERVICE_CLIENT_EXTERNAL_ID)
            .setNewRoleTitles(Collections.singletonList(ROLE_TO_ADD));
    }

    public static ChangeSystemClientRoleValidationDto changeSystemClientRoleValidationDto() {
        return new ChangeSystemClientRoleValidationDto()
            .setServiceClientRoles(Collections.singletonList(ROLE_TO_REMOVE))
            .setRequest(changeServiceClientRolesRequestDto());
    }

    public static ChangeServiceClientRolesRequestDto changeServiceClientRolesRequestDto() {
        return new ChangeServiceClientRolesRequestDto()
            .setRolesToAdd(Collections.singletonList(ROLE_TO_ADD))
            .setRolesToRemove(Collections.singletonList(ROLE_TO_REMOVE));
    }

    public static ServiceClientRegistrationResponseDto serviceClientRegistrationResponseDto() {
        return new ServiceClientRegistrationResponseDto()
            .setUsername(USERNAME)
            .setRegistrationDate(REGISTRATION_DATE)
            .setUserRoles(Collections.singletonList(ROLE_TITLE))
            .setClientExternalId(SERVICE_CLIENT_EXTERNAL_ID);
    }

    public static ServiceClientRegistrationValidationDto serviceClientRegistrationValidationDto() {
        return new ServiceClientRegistrationValidationDto()
            .setCurrentScope(ANON_SCOPE)
            .setAnonRegistrationEnabled(ANON_REGISTRATION_ENABLED)
            .setUsernameAlreadyExists(USERNAME_ALREADY_EXISTS)
            .setDateOfBirth(DATE_OF_BIRTH);
    }

    public static ServiceClientRegistrationRequestDto serviceClientRegistrationRequestDto() {
        return new ServiceClientRegistrationRequestDto()
            .setAuthInformation(serviceClientAuthInformationDto())
            .setAdditionalInformation(serviceClientAdditionalInformationDto());
    }

    public static ServiceClientAuthInformationDto serviceClientAuthInformationDto() {
        return new ServiceClientAuthInformationDto()
            .setClientId(CLIENT_ID)
            .setUsername(USERNAME)
            .setPassword(PLAIN_PASSWORD)
            .setServiceClientRoles(Collections.singletonList(ROLE_TITLE));
    }

    public static ServiceClientAdditionalInformationDto serviceClientAdditionalInformationDto() {
        return new ServiceClientAdditionalInformationDto()
            .setName(NAME)
            .setSurname(SURNAME)
            .setMiddleName(MIDDLE_NAME)
            .setEmail(EMAIL)
            .setDateOfBirth(DATE_OF_BIRTH)
            .setAdditionalInfo(Collections.singletonList(serviceClientAdditionalInfoRecordDto()));
    }

    public static ServiceClientAdditionalInfoRecordDto serviceClientAdditionalInfoRecordDto() {
        return new ServiceClientAdditionalInfoRecordDto()
            .setField(ADDITIONAL_FIELD_NAME)
            .setValue(ADDITIONAL_VALUE);
    }

    public static CreateRoleRequestDto createRoleRequestDto() {
        return new CreateRoleRequestDto()
            .setRoleTitle(ROLE_TITLE)
            .setScopes(Collections.singletonList(Scope.ANON));
    }

    public static CreateRoleValidationDto createRoleValidationDto() {
        return new CreateRoleValidationDto()
            .setRoleExists(Boolean.FALSE)
            .setRoleTitle(ROLE_TITLE);
    }

    public static SystemRegistrationValidationDto systemRegistrationValidationDto() {
        return new SystemRegistrationValidationDto()
            .setRequest(systemRegistrationRequestDto())
            .setClientExists(Boolean.FALSE);
    }

    public static SystemRegistrationRequestDto systemRegistrationRequestDto() {
        return new SystemRegistrationRequestDto()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .setRegistrationPassword(REGISTRATION_PASSWORD)
            .setAccessTokenValidity(ACCESS_TOKEN_VALIDITY)
            .setRefreshTokenValidity(REFRESH_TOKEN_VALIDITY)
            .setAnonRegistrationEnabled(Boolean.TRUE);
    }

    public static ErrorDto errorDto() {
        return new ErrorDto()
                .setCode(EXCEPTION_CODE)
                .setMessage(EXCEPTION_MESSAGE);
    }

    public static ValidationErrorDto validationErrorDto() {
        return new ValidationErrorDto(Collections.singletonList(errorDto()));
    }

    public static ValidationException validationException() {
        ValidationException validationException = new ValidationException();
        validationException.addCode(ExceptionCode.INTERNAL_ERROR);
        return validationException;
    }
}
