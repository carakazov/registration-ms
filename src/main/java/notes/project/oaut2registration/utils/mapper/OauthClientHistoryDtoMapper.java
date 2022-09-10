package notes.project.oaut2registration.utils.mapper;

import java.util.List;

import notes.project.oaut2registration.dto.OauthClientHistoryDto;
import notes.project.oaut2registration.model.OauthClientHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OauthClientHistoryDtoMapper {
    @Mapping(target = "clientSystem", source = "oauthClient.clientId")
    @Mapping(target = "operator", source = "oauthAdmin.clientId")
    @Mapping(target = "event", source = "oauthEvent")
    OauthClientHistoryDto to(OauthClientHistory source);

    List<OauthClientHistoryDto> to(List<OauthClientHistory> source);
}
