package notes.project.oaut2registration.service.integration;

import notes.project.oaut2registration.model.RestorePasswordStruct;

public interface RestorePasswordRequestProducer {
    void produceMessage(RestorePasswordStruct restorePasswordStruct, String contact);
}
