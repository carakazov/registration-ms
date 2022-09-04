package notes.project.oaut2registration.utils.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.dto.ServiceClientAdditionalInformationDto;
import notes.project.oaut2registration.model.ServiceClient;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SendAdditionalInfoMappingDto {
    private ServiceClientAdditionalInformationDto additionalInfo;
    private ServiceClient serviceClient;
}
