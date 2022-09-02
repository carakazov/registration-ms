package notes.project.oaut2registration.repository;

import java.util.Optional;

import notes.project.oaut2registration.model.ServiceClientHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceClientHistoryRepository extends JpaRepository<ServiceClientHistory, Long> {

}
