package notes.project.filesystem.utils;

import lombok.experimental.UtilityClass;
import notes.project.filesystem.config.ApplicationProperties;

@UtilityClass
public class ApplicationPropertiesUtils {
    public static ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }
}
