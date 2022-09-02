package notes.project.oaut2registration.service.api;

public interface OauthAccessTokenService {
    void deleteAccessTokenByClientIdAndUserName(String clientId, String username);
}
