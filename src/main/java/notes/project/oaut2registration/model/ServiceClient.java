package notes.project.oaut2registration.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Data
@Accessors(chain = true)
@Entity(name = "service_clients")
@EntityListeners(AuditingEntityListener.class)
public class ServiceClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID externalId = UUID.randomUUID();

    private String username;

    private String password;

    @CreatedDate
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private OauthClientDetails oauthClient;

    private Boolean blocked;

    @ManyToMany
    @JoinTable(
        name = "service_client_role",
        joinColumns = @JoinColumn(name = "service_client_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}
