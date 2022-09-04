package notes.project.oaut2registration.config.oauth;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ExternalIdEnhancer implements TokenEnhancer {
    private static final String EXTERNAL_ID_CLAIM = "externalId";

    @Override
    public OAuth2AccessToken enhance(
        OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication
    ) {
        if(oAuth2Authentication.getPrincipal() instanceof User) {
            ClientDetails clientDetails = (ClientDetails) oAuth2Authentication.getPrincipal();
            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put(EXTERNAL_ID_CLAIM, clientDetails.getExternalId());
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        }
        return oAuth2AccessToken;
    }
}
