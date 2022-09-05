package notes.project.oaut2registration.utils.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.RestorePasswordStruct;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RestorePasswordRequestKafkaMappingDto {
    private RestorePasswordStruct struct;
    private String contact;
}
