package notes.project.oaut2registration.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "auth_service_roles")
public class AuthServiceRole {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthServiceRoleTitle authServiceRoleTitle;
}
