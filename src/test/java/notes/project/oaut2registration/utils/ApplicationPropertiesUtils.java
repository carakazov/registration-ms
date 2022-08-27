package notes.project.oaut2registration.utils;

import java.util.Collections;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.config.ApplicationProperties;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

import static notes.project.oaut2registration.utils.TestDataConstants.*;

@UtilityClass
public class ApplicationPropertiesUtils {
    public static ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    public static ApplicationProperties applicationPropertiesForRegisterSystemValidator() {
        return new ApplicationProperties()
            .setSystemRegistrationKeyWords(Collections.singletonList(REGISTRATION_PASSWORD))
            .setMaxAccessTokenValidity(MAX_ACCESS_TOKEN_VALIDITY)
            .setMinAccessTokenValidity(MIN_ACCESS_TOKEN_VALIDITY)
            .setMaxRefreshTokenValidity(MAX_REFRESH_TOKEN_VALIDITY)
            .setMinRefreshTokenValidity(MIN_REFRESH_TOKEN_VALIDITY);
    }
}
