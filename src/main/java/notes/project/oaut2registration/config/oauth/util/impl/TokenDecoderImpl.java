package notes.project.oaut2registration.config.oauth.util.impl;



import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import notes.project.oaut2registration.config.oauth.dto.JwtDto;
import notes.project.oaut2registration.config.oauth.util.TokenDecoder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TokenDecoderImpl implements TokenDecoder {

    @Override
    public JwtDto decode(String tokenHeader) {
        String token = tokenHeader.substring(7);
        String bodyEncoded = token.split("\\.")[1];

        Base64 base64 = new Base64(true);
        String body = new String(base64.decode(bodyEncoded));
        DecodedJWT decodedJWT = JWT.decode(token);
        JwtDto jwtDto = new Gson().fromJson(body, JwtDto.class);
        jwtDto.setExpiresAt(decodedJWT.getExpiresAt());
        return jwtDto;
    }
}
