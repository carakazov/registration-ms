package notes.project.oaut2registration.utils.mapper;

import java.util.List;
import java.util.stream.Collectors;

import notes.project.oaut2registration.dto.ServiceClientDto;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.ServiceClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ServiceListMapper {
    @Mapping(target = "roles", qualifiedByName = "mapRoles",  source = "source")
    ServiceClientDto to(ServiceClient source);

    List<ServiceClientDto> to(List<ServiceClient> source);

    @Named("mapRoles")
    default List<String> mapRoles(ServiceClient source) {
        return source.getRoles().stream().map(Role::getRoleTitle).collect(Collectors.toList());
    }
}
