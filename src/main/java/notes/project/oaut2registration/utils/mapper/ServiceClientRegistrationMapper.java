package notes.project.oaut2registration.utils.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import notes.project.oaut2registration.dto.api.ServiceClientAuthInformationDto;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationResponseDto;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.utils.mapper.dto.ServiceClientRegistrationMappingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceClientRegistrationMapper {
    @Mapping(target = "blocked", constant = "false")
    ServiceClient to(ServiceClientRegistrationMappingDto source);

    @Mapping(target = "username", source = "authInfo.username")
    @Mapping(target = "userRoles", source = "authInfo.serviceClientRoles")
    @Mapping(target = "registrationDate", source = "registrationDate")
    ServiceClientRegistrationResponseDto from(ServiceClientAuthInformationDto authInfo, LocalDateTime registrationDate);
}
