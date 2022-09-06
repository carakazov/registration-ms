package notes.project.oaut2registration.service.api.impl;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.RestorePasswordStruct;
import notes.project.oaut2registration.repository.RestorePasswordStructRepository;
import notes.project.oaut2registration.service.api.RestorePasswordStructService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestorePasswordStructServiceImpl implements RestorePasswordStructService {
    private final RestorePasswordStructRepository repository;

    @Override
    public RestorePasswordStruct save(RestorePasswordStruct restorePasswordStruct) {
        return repository.save(restorePasswordStruct);
    }

    @Override
    public RestorePasswordStruct findByRestoreCode(String restoreCode) {
        return repository.findByRestoreCode(restoreCode)
            .orElseThrow(() -> new NotFoundException("Restore code " + restoreCode + " not found"));
    }

    @Override
    @Transactional
    public RestorePasswordStruct changeStructInProcessStatus(RestorePasswordStruct struct) {
        struct.setInProcess(!struct.getInProcess());
        return struct;
    }
}
