package notes.project.oaut2registration.repository;

import notes.project.oaut2registration.model.OauthClientHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientHistoryRepository extends JpaRepository<OauthClientHistory, Long> {
}
