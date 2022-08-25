package notes.project.oaut2registration.config.oauth.service;

import java.util.List;

import notes.project.oaut2registration.config.oauth.dto.JwtDto;
import notes.project.oaut2registration.model.Scope;

public interface InnerScopeFilterService {
    List<String> extractScopes(JwtDto jwtDto);
}
