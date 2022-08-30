package notes.project.oaut2registration.utils.uuid.impl;

import java.util.UUID;

import notes.project.oaut2registration.utils.uuid.UuidHelper;
import org.springframework.stereotype.Component;

@Component
public class UuidHelperImpl implements UuidHelper {
    @Override
    public UUID generateUuid() {
        return UUID.randomUUID();
    }
}
