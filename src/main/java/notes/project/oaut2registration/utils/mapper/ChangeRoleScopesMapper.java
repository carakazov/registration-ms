package notes.project.oaut2registration.utils.mapper;

import java.util.List;
import java.util.stream.Collectors;

import notes.project.oaut2registration.dto.ChangeRoleScopesResponseDto;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.SystemScope;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ChangeRoleScopesMapper {
    @Mapping(target = "newScopesList", source = "source", qualifiedByName = "mapScopes")
    ChangeRoleScopesResponseDto to(Role source);

    @Named("mapScopes")
    default List<Scope> mapScopes(Role role) {
        return role.getScopes().stream().map(SystemScope::getSystemScope).collect(Collectors.toList());
    }
}
