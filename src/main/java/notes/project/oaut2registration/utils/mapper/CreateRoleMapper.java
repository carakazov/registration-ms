package notes.project.oaut2registration.utils.mapper;

import notes.project.oaut2registration.dto.CreateRoleRequestDto;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.utils.mapper.dto.CreateRoleMappingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateRoleMapper {

    Role to(CreateRoleMappingDto source);
}
