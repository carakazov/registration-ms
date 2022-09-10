package notes.project.oaut2registration.model;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Accessors(chain = true)
@Entity(name = "oauth_client_history")
@EntityListeners(AuditingEntityListener.class)
public class OauthClientHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oauth_client")
    private OauthClientDetails oauthClient;

    @ManyToOne
    @JoinColumn(name = "oauth_admin")
    private OauthClientDetails oauthAdmin;

    @Enumerated(EnumType.STRING)
    private OauthEvent oauthEvent;

    @CreatedDate
    private LocalDateTime eventDate;
}
