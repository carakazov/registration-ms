package notes.project.oaut2registration.repository;

import notes.project.oaut2registration.model.OauthAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthAccessTokenRepository extends JpaRepository<OauthAccessToken, String> {
    boolean existsByClientIdAndUserName(String clientId, String username);
    void deleteByClientIdAndUserName(String clientId, String username);
}
