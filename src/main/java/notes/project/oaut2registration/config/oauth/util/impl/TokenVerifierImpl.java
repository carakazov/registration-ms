package notes.project.oaut2registration.config.oauth.util.impl;

import java.time.LocalDateTime;
import java.util.Date;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.config.oauth.dto.JwtDto;
import notes.project.oaut2registration.config.oauth.util.TokenVerifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenVerifierImpl implements TokenVerifier {
    @Override
    public void verify(JwtDto jwt) {
        expired(jwt.getExpiresAt());
    }

    private void expired(Date expirationDate) {
        if(expirationDate.before(new Date())) {
            throw new TokenExpiredException("Token has expired", expirationDate.toInstant());
        }
    }
}
