package notes.project.oaut2registration.utils.mapper;

import notes.project.oaut2registration.model.ServiceClientHistory;
import notes.project.oaut2registration.utils.mapper.dto.ServiceClientHistoryMappingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceClientHistoryMapper {
    ServiceClientHistory to(ServiceClientHistoryMappingDto source);
}
