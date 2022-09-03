package notes.project.oaut2registration.repository;

import java.util.List;
import java.util.Optional;

import liquibase.pro.packaged.S;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.SystemScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemScopeRepository extends JpaRepository<SystemScope, Long> {
    SystemScope findBySystemScope(Scope scope);
    List<SystemScope> findAllBySystemScope(Scope scope);
}
