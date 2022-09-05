package notes.project.oaut2registration.utils;

import dto.integration.kafka.RestorePasswordRequestKafkaDto;
import dto.integration.kafka.ServiceClientAdditionalInfoKafkaDto;
import dto.integration.kafka.ServiceClientAdditionalInfoRecordKafkaDto;
import lombok.experimental.UtilityClass;

import static notes.project.oaut2registration.utils.TestDataConstants.*;

@UtilityClass
public class IntegrationUtils {

    public static RestorePasswordRequestKafkaDto restorePasswordRequestKafkaDto() {
        RestorePasswordRequestKafkaDto dto = new RestorePasswordRequestKafkaDto();
        dto.setClientId(CLIENT_ID);
        dto.setRestoreCode(RESTORE_CODE);
        dto.setContact(EMAIL);
        return dto;
    }

    public static ServiceClientAdditionalInfoKafkaDto serviceClientAdditionalInfoKafkaDto() {
        ServiceClientAdditionalInfoKafkaDto dto = new ServiceClientAdditionalInfoKafkaDto();
        dto.setName(NAME);
        dto.setSurname(SURNAME);
        dto.setMiddleName(MIDDLE_NAME);
        dto.setEmail(EMAIL);
        dto.setDateOfBirth(DATE_OF_BIRTH);
        dto.getAdditionalInfo().add(serviceClientAdditionalInfoRecordKafkaDto());
        dto.setExternalId(SERVICE_CLIENT_EXTERNAL_ID);
        dto.setRegistrationDate(REGISTRATION_DATE);
        return dto;
    }

    public static ServiceClientAdditionalInfoRecordKafkaDto serviceClientAdditionalInfoRecordKafkaDto() {
        ServiceClientAdditionalInfoRecordKafkaDto dto = new ServiceClientAdditionalInfoRecordKafkaDto();
        dto.setField(ADDITIONAL_FIELD_NAME);
        dto.setValue(ADDITIONAL_VALUE);
        return dto;
    }
}
