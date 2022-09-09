package notes.project.oaut2registration.model;

import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleTitle;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private OauthClientDetails details;

    @ManyToMany
    @JoinTable(
        name = "system_scope_role",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "system_scope_id")
    )
    private List<SystemScope> scopes;

    private Boolean blocked = Boolean.FALSE;
}
