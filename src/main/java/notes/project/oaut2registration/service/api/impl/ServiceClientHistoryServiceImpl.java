package notes.project.oaut2registration.service.api.impl;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.model.ServiceClientHistory;
import notes.project.oaut2registration.repository.ServiceClientHistoryRepository;
import notes.project.oaut2registration.service.api.ServiceClientHistoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceClientHistoryServiceImpl implements ServiceClientHistoryService {
    private final ServiceClientHistoryRepository repository;
    @Override
    @Transactional
    public ServiceClientHistory save(ServiceClientHistory serviceClientHistory) {
        return repository.save(serviceClientHistory);
    }
}
