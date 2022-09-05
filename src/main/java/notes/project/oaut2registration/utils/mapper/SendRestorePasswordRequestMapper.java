package notes.project.oaut2registration.utils.mapper;

import dto.integration.kafka.RestorePasswordRequestKafkaDto;
import notes.project.oaut2registration.utils.mapper.dto.RestorePasswordRequestKafkaMappingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SendRestorePasswordRequestMapper {

    @Mapping(target = "clientId", source = "struct.details.clientId")
    @Mapping(target = "restoreCode", source = "struct.restoreCode")
    @Mapping(target = "contact", source = "contact")
    RestorePasswordRequestKafkaDto to(RestorePasswordRequestKafkaMappingDto source);
}
