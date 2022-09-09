package notes.project.oaut2registration.repository;

import java.util.Optional;
import java.util.UUID;

import notes.project.oaut2registration.model.ServiceClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceClientRepository extends JpaRepository<ServiceClient, Long> {
    ServiceClient findByUsernameAndOauthClientClientId(String username, String clientId);
    Boolean existsByUsernameAndOauthClientClientId(String username, String clientId);
    Optional<ServiceClient> findByExternalId(UUID externalId);
    Optional<ServiceClient> findByExternalIdAndOauthClientClientId(UUID externalId, String clientId);
}
