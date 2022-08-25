package notes.project.oaut2registration.repository;

import notes.project.oaut2registration.model.ServiceClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceClientRepository extends JpaRepository<ServiceClient, Long> {
    ServiceClient findByUsernameAndOauthClientClientId(String username, String clientId);
}
