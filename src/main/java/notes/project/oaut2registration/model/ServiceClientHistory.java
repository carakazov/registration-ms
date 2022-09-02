package notes.project.oaut2registration.model;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Accessors(chain = true)
@Entity(name = "service_client_history")
@EntityListeners(AuditingEntityListener.class)
public class ServiceClientHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_client_id")
    private ServiceClient client;

    @CreatedDate
    private LocalDateTime eventDate;

    @Enumerated(EnumType.STRING)
    private HistoryEvent event;

    @ManyToOne
    @JoinColumn(name = "operator_client_id")
    private ServiceClient operator;
}
