package notes.project.oaut2registration.utils.mapper;

import notes.project.oaut2registration.model.RestorePasswordStruct;
import notes.project.oaut2registration.utils.mapper.dto.RestorePasswordStructMappingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestorePasswordStructMapper {
    RestorePasswordStruct to(RestorePasswordStructMappingDto source);
}
