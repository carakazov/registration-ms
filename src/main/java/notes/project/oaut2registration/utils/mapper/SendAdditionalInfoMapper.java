package notes.project.oaut2registration.utils.mapper;

import dto.integration.kafka.ServiceClientAdditionalInfoKafkaDto;
import notes.project.oaut2registration.utils.mapper.dto.SendAdditionalInfoMappingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SendAdditionalInfoMapper {

    @Mapping(target = "name", source = "additionalInfo.name")
    @Mapping(target = "surname", source = "additionalInfo.surname")
    @Mapping(target = "middleName", source = "additionalInfo.middleName")
    @Mapping(target = "email", source = "additionalInfo.email")
    @Mapping(target = "dateOfBirth", source = "additionalInfo.dateOfBirth")
    @Mapping(target = "additionalInfo", source = "additionalInfo.additionalInfo")
    @Mapping(target = "externalId", source = "serviceClient.externalId")
    @Mapping(target = "registrationDate", source = "serviceClient.registrationDate")
    @Mapping(target = "systemName", source = "serviceClient.oauthClient.clientId")
    ServiceClientAdditionalInfoKafkaDto to(SendAdditionalInfoMappingDto source);
}
