package notes.project.oaut2registration.utils.mapper;

import java.util.List;
import java.util.stream.Collectors;

import notes.project.oaut2registration.dto.ChangeServiceClientRolesResponseDto;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.ServiceClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ChangeServiceClientRolesResponseMapper {
    @Mapping(target = "userExternalId", source = "externalId")
    @Mapping(target = "newRoleTitles", source = "serviceClient.roles", qualifiedByName = "mapRoles")
    ChangeServiceClientRolesResponseDto to(ServiceClient serviceClient);

    @Named("mapRoles")
    default List<String> mapRoles(List<Role> roles) {
        return roles.stream().map(Role::getRoleTitle).collect(Collectors.toList());
    }
}
