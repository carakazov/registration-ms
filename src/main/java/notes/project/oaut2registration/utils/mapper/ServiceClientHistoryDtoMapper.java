package notes.project.oaut2registration.utils.mapper;

import java.util.List;

import notes.project.oaut2registration.dto.ServiceClientHistoryDto;
import notes.project.oaut2registration.model.ServiceClientHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceClientHistoryDtoMapper {

    @Mapping(target = "username", source = "client.username")
    @Mapping(target = "operator", source = "operator.username")
    ServiceClientHistoryDto to(ServiceClientHistory source);

    List<ServiceClientHistoryDto> to(List<ServiceClientHistory> source);
}
