package notes.project.oaut2registration.utils;

import lombok.experimental.UtilityClass;
import notes.project.oaut2registration.config.ApplicationProperties;

@UtilityClass
public class ApplicationPropertiesUtils {
    public static ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }
}
