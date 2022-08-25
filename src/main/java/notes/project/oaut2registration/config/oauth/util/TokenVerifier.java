package notes.project.oaut2registration.config.oauth.util;

import notes.project.oaut2registration.config.oauth.dto.JwtDto;

public interface TokenVerifier {
    void verify(JwtDto jwt);
}
