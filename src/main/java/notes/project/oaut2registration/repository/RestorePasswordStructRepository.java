package notes.project.oaut2registration.repository;

import java.util.Optional;

import notes.project.oaut2registration.model.RestorePasswordStruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestorePasswordStructRepository extends JpaRepository<RestorePasswordStruct, Long> {
    Optional<RestorePasswordStruct> findByRestoreCode(String restoreCode);
}
