package notes.project.oaut2registration.utils;

import java.util.Collections;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.dto.*;
import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.exception.ValidationException;
import notes.project.oaut2registration.model.HistoryEvent;
import notes.project.oaut2registration.model.OauthEvent;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.utils.validation.dto.*;

import static notes.project.oaut2registration.utils.TestDataConstants.*;

@UtilityClass
public class ApiUtils {
    public static ClientDtoListResponseDto<OauthClientDto> oauthClientResponseDto() {
        return new ClientDtoListResponseDto<>(Collections.singletonList(oauthClientDto()));
    }

    public static ClientDtoListResponseDto<ServiceClientDto> serviceClientResponseDto() {
        return new ClientDtoListResponseDto<>(Collections.singletonList(serviceClientDto()));
    }

    public static ServiceClientDto serviceClientDto() {
        return (ServiceClientDto) new ServiceClientDto()
            .setExternalId(SERVICE_CLIENT_EXTERNAL_ID)
            .setRegistrationDate(REGISTRATION_DATE)
            .setRoles(Collections.singletonList(ROLE_TITLE))
            .setUsername(USERNAME)
            .setBlocked(Boolean.FALSE);
    }

    public static OauthClientDto oauthClientDto() {
        return new OauthClientDto()
            .setUsername(CLIENT_ID)
            .setBlocked(Boolean.FALSE);
    }

    public static ServiceClientHistoryListResponseDto serviceClientHistoryListResponseDto() {
        return new ServiceClientHistoryListResponseDto(Collections.singletonList(serviceClientHistoryDto()));
    }

    public static ServiceClientHistoryDto serviceClientHistoryDto() {
        return new ServiceClientHistoryDto()
            .setUsername(USERNAME)
            .setOperator(OPERATOR_USERNAME)
            .setEvent(HistoryEvent.BLOCKED)
            .setEventDate(EVENT_DATE);
    }

    public static OauthClientHistoryListResponseDto oauthClientHistoryListResponseDto() {
        return new OauthClientHistoryListResponseDto(Collections.singletonList(oauthClientHistoryDto()));
    }

    public static OauthClientHistoryDto oauthClientHistoryDto() {
        return new OauthClientHistoryDto()
            .setClientSystem(CLIENT_ID)
            .setOperator(OPERATOR_CLIENT_ID)
            .setEvent(OauthEvent.BLOCKED)
            .setEventDate(OAUTH_EVENT_DATE);
    }

    public static RestorePasswordValidationDto restorePasswordValidationDto() {
        return new RestorePasswordValidationDto()
            .setDetails(DbUtils.oauthClientDetails())
            .setServiceClient(DbUtils.serviceClient())
            .setInProcess(Boolean.TRUE);
    }

    public static InitializePasswordRestoreRequestDto initializePasswordRestoreRequestDto() {
        return new InitializePasswordRestoreRequestDto()
            .setClientId(CLIENT_ID)
            .setContact(EMAIL)
            .setNewPassword(NEW_PASSWORD_PLAIN);
    }

    public static ChangePasswordValidationDto changePasswordValidationDto() {
        return new ChangePasswordValidationDto()
            .setRequest(changePasswordRequestDto())
            .setCurrentPassword(ENCODED_PASSWORD);
    }

    public static ChangePasswordRequestDto changePasswordRequestDto() {
        return new ChangePasswordRequestDto()
            .setExternalId(SERVICE_CLIENT_EXTERNAL_ID)
            .setOldPassword(PLAIN_PASSWORD)
            .setNewPassword(NEW_PASSWORD_PLAIN);
    }

    public static ChangeServiceClientRolesResponseDto changeServiceClientRolesResponseDto() {
        return new ChangeServiceClientRolesResponseDto()
            .setUserExternalId(SERVICE_CLIENT_EXTERNAL_ID)
            .setNewRoleTitles(Collections.singletonList(ROLE_TO_ADD));
    }

    public static ChangeRoleScopesResponseDto changeRoleScopesResponseDto() {
        return new ChangeRoleScopesResponseDto()
            .setRoleTitle(ROLE_TITLE)
            .setNewScopesList(Collections.singletonList(Scope.CHANGE_ROLES));
    }

    public static ChangeAssignedResourcesRequestDto<Scope> changeAssignedResourcesRequestDtoScope() {
        return new ChangeAssignedResourcesRequestDto<Scope>()
            .setToAdd(Collections.singletonList(Scope.CHANGE_ROLES))
            .setToRemove(Collections.singletonList(Scope.ANON));
    }

    public static ChangeAssignedResourcesValidationDto<String> changeSystemClientRoleValidationDtoString() {
        return new ChangeAssignedResourcesValidationDto<String>()
            .setExistingList(Collections.singletonList(ROLE_TO_REMOVE))
            .setRequest(changeServiceClientRolesRequestDtoString());
    }

    public static ChangeAssignedResourcesRequestDto<String> changeServiceClientRolesRequestDtoString() {
        return new ChangeAssignedResourcesRequestDto<String>()
            .setToAdd(Collections.singletonList(ROLE_TO_ADD))
            .setToRemove(Collections.singletonList(ROLE_TO_REMOVE));
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
