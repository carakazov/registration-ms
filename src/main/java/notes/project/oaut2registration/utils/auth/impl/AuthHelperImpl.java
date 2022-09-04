package notes.project.oaut2registration.utils.auth.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.config.oauth.dto.JwtDto;
import notes.project.oaut2registration.exception.SecurityContextException;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthHelperImpl implements AuthHelper {
    @Override
    public String getClientId() {
        Object clientId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(Objects.isNull(clientId)) {
            throw new SecurityContextException("No principal found in context");
        }
        return clientId.toString();
    }

    @Override
    public String getCurrentUserName() {
        JwtDto jwt = extractJwt();
        if(Objects.isNull(jwt.getUserName())) {
            throw new SecurityContextException("No username found in context");
        }
        return jwt.getUserName();
    }

    @Override
    public String getCurrentAuthority() {
        return List.of(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()).get(0).toString();
    }

    @Override
    public UUID getCurrentExternalId() {
        JwtDto jwtDto = extractJwt();
        if(Objects.isNull(jwtDto.getExternalId())) {
            throw new SecurityContextException("No external id found in context");
        }
        return jwtDto.getExternalId();
    }

    private JwtDto extractJwt() {
        return (JwtDto) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }
}
