package notes.project.oaut2registration.dto.integration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ServiceClientAdditionalInfoKafkaDto {
    private String name;
    private String surname;
    private String middleName;
    private String email;
    private LocalDate dateOfBirth;
    private Map<String, Object> additionalInfo;
    private UUID externalId;
    private LocalDateTime registrationDate;
}
