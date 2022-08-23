package notes.project.filesystem.utils;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.experimental.UtilityClass;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.model.FileResolution;

@UtilityClass
public class TestDataConstants {
    //Common
    public static Long ID = 1L;
    public static Long ID_2 = 2L;
    public static final String EXCEPTION_CODE = "unexpectedErrorWhileCreationOperation";
    public static final String EXCEPTION_MESSAGE = "exception message";
}
