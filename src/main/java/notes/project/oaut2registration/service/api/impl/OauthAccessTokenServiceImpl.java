package notes.project.oaut2registration.service.api.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.model.OauthAccessToken;
import notes.project.oaut2registration.repository.OauthAccessTokenRepository;
import notes.project.oaut2registration.service.api.OauthAccessTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthAccessTokenServiceImpl implements OauthAccessTokenService {
    private final OauthAccessTokenRepository repository;

    @Override
    public void deleteAccessTokenByClientIdAndUserName(String clientId, String username) {
        if(repository.existsByClientIdAndUserName(clientId, username)) {
            repository.deleteByClientIdAndUserName(clientId, username);
        }
    }
}
