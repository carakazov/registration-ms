package notes.project.oaut2registration.service.api.impl;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.model.OauthClientHistory;
import notes.project.oaut2registration.repository.OauthClientHistoryRepository;
import notes.project.oaut2registration.service.api.OauthClientHistoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthClientHistoryServiceImpl implements OauthClientHistoryService {
    private final OauthClientHistoryRepository repository;

    @Override
    @Transactional
    public OauthClientHistory save(OauthClientHistory oauthClientHistory) {
        return repository.save(oauthClientHistory);
    }
}
