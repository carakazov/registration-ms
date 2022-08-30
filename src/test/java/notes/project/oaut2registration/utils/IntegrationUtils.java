package notes.project.oaut2registration.utils;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.dto.integration.ServiceClientAdditionalInfoKafkaDto;

import static notes.project.oaut2registration.utils.TestDataConstants.*;

@UtilityClass
public class IntegrationUtils {
    public static ServiceClientAdditionalInfoKafkaDto serviceClientAdditionalInfoKafkaDto() {
        return new ServiceClientAdditionalInfoKafkaDto()
            .setName(NAME)
            .setSurname(SURNAME)
            .setMiddleName(MIDDLE_NAME)
            .setEmail(EMAIL)
            .setDateOfBirth(DATE_OF_BIRTH)
            .setAdditionalInfo(ADDITIONAL_INFO)
            .setExternalId(SERVICE_CLIENT_EXTERNAL_ID)
            .setRegistrationDate(REGISTRATION_DATE);
    }
}
