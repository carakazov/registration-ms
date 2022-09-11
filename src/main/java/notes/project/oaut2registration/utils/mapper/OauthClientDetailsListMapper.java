package notes.project.oaut2registration.utils.mapper;

import java.util.List;

import notes.project.oaut2registration.dto.OauthClientDto;
import notes.project.oaut2registration.model.OauthClientDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OauthClientDetailsListMapper {
    @Mapping(target = "username", source = "clientId")
    OauthClientDto to(OauthClientDetails source);

    List<OauthClientDto> to(List<OauthClientDetails> source);
}
