package notes.project.oaut2registration.config.oauth;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notes.project.oaut2registration.config.oauth.dto.JwtDto;
import notes.project.oaut2registration.config.oauth.service.InnerScopeFilterService;
import notes.project.oaut2registration.config.oauth.util.TokenDecoder;
import notes.project.oaut2registration.config.oauth.util.TokenVerifier;
import notes.project.oaut2registration.config.oauth.util.impl.TokenDecoderImpl;
import notes.project.oaut2registration.config.oauth.util.impl.TokenVerifierImpl;
import notes.project.oaut2registration.model.Scope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class InnerScopeFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenDecoder tokenDecoder = new TokenDecoderImpl();
    private final TokenVerifier tokenVerifier = new TokenVerifierImpl();
    private final InnerScopeFilterService innerScopeFilterService;

    public InnerScopeFilter(InnerScopeFilterService innerScopeFilterService) {
        this.innerScopeFilterService = innerScopeFilterService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        Authentication authentication;
        if(StringUtils.isNotEmpty(authHeader)) {
            JwtDto jwtDto = tokenDecoder.decode(authHeader);
            tokenVerifier.verify(jwtDto);
            List<String> scopes = innerScopeFilterService.extractScopes(jwtDto);
            authentication = new UsernamePasswordAuthenticationToken(
                jwtDto.getClientId(),
                jwtDto,
                scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
        } else {
            authentication = new UsernamePasswordAuthenticationToken(
                null,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(Scope.ANON.toString()))
            );
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
