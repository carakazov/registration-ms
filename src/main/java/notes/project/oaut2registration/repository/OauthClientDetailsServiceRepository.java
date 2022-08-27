package notes.project.oaut2registration.repository;

import java.util.Optional;
import java.util.OptionalInt;

import notes.project.oaut2registration.model.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientDetailsServiceRepository extends JpaRepository<OauthClientDetails, String> {
    boolean existsByClientId(String clientId);

    Optional<OauthClientDetails> findByClientId(String clientId);
}
