package notes.project.oaut2registration.repository;

import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByDetailsAndRoleTitle(OauthClientDetails details, String roleTitle);
}
