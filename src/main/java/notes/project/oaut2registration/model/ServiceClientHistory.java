package notes.project.oaut2registration.model;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "service_client_history")
public class ServiceClientHistory {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_client_id")
    private ServiceClient client;

    private LocalDateTime eventDate;

    @Enumerated(EnumType.STRING)
    private HistoryEvent event;

    @ManyToOne
    @JoinColumn(name = "operator_client_id")
    private ServiceClient operator;
}
