package notes.project.oaut2registration.utils.mapper;

import liquibase.pro.packaged.M;
import notes.project.oaut2registration.dto.integration.ServiceClientAdditionalInfoKafkaDto;
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
    @Mapping(target = "externalId", source = "externalId")
    @Mapping(target = "registrationDate", source = "registrationDate")
    ServiceClientAdditionalInfoKafkaDto to(SendAdditionalInfoMappingDto source);
}
