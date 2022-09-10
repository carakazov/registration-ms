package notes.project.oaut2registration.utils.mapper;

import notes.project.oaut2registration.model.OauthClientHistory;
import notes.project.oaut2registration.utils.mapper.dto.OauthClientHistoryMappingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OauthClientHistoryMapper {

    OauthClientHistory to(OauthClientHistoryMappingDto source);
}
