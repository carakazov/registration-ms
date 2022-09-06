package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.model.RestorePasswordStruct;

public interface RestorePasswordStructService {
    RestorePasswordStruct save(RestorePasswordStruct restorePasswordStruct);

    RestorePasswordStruct findByRestoreCode(String restoreCode);

    RestorePasswordStruct changeStructInProcessStatus(RestorePasswordStruct struct);
}
