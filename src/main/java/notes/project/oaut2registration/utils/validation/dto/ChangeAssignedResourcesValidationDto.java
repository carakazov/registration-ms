package notes.project.oaut2registration.utils.validation.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.dto.ChangeAssignedResourcesRequestDto;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAssignedResourcesValidationDto<T> {
    private List<T> existingList;
    private ChangeAssignedResourcesRequestDto<T> request;
}
