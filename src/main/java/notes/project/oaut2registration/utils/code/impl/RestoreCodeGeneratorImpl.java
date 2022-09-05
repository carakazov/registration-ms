package notes.project.oaut2registration.utils.code.impl;

import java.util.Random;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.utils.code.RestoreCodeGenerator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestoreCodeGeneratorImpl implements RestoreCodeGenerator {
    private static final Integer RESTORE_CODE_LENGTH = 25;
    private static final Integer LEFT_LIMIT = 48;
    private static final Integer RIGHT_LIMIT = 122;
    private static final Random RANDOM = new Random();

    @Override
    public String generate() {
        return RANDOM.ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(RESTORE_CODE_LENGTH)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

}
