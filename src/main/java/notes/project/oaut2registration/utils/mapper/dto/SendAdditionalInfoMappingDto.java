package notes.project.oaut2registration.utils.mapper.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.dto.api.ServiceClientAdditionalInformationDto;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SendAdditionalInfoMappingDto {
    private ServiceClientAdditionalInformationDto additionalInfo;
    private UUID externalId;
    private LocalDateTime registrationDate;
}
