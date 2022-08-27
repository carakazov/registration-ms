package notes.project.oaut2registration.repository;

import notes.project.oaut2registration.model.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientDetailsServiceRepository extends JpaRepository<OauthClientDetails, String> {
    boolean existsByClientId(String clientId);
}
