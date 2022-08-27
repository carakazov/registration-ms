package notes.project.oaut2registration.config;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "application")
@Component
@Accessors(chain = true)
public class ApplicationProperties {

    private Map<String, String> errorMessages;

    private List<String> systemRegistrationKeyWords;
    private Integer maxAccessTokenValidity;
    private Integer minAccessTokenValidity;
    private Integer maxRefreshTokenValidity;
    private Integer minRefreshTokenValidity;

    public String getMessage(String messageCode) {
        return errorMessages.get(messageCode);
    }
}
