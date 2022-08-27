package notes.project.oaut2registration.utils.mapper;

import notes.project.oaut2registration.dto.SystemRegistrationRequestDto;
import notes.project.oaut2registration.model.Authority;
import notes.project.oaut2registration.model.OauthClientDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = Authority.class)
public interface CreateOauthClientDetailsMapper {

    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "resourceIds", ignore = true)
    @Mapping(target = "clientSecret", source = "clientSecret")
    @Mapping(target = "scope", constant = "any")
    @Mapping(target = "authorizedGrantTypes", constant = "password,refresh_token,client_credentials")
    @Mapping(target = "webServerRedirectUri", ignore = true)
    @Mapping(target = "authorities", expression = "java(Authority.OAUTH_CLIENT.toString())")
    @Mapping(target = "accessTokenValidity", source = "accessTokenValidity")
    @Mapping(target = "refreshTokenValidity", source = "refreshTokenValidity")
    @Mapping(target = "autoapprove", ignore = true)
    OauthClientDetails from(SystemRegistrationRequestDto source);
}
