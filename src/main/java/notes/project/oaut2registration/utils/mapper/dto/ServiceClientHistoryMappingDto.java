package notes.project.oaut2registration.utils.mapper.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.HistoryEvent;
import notes.project.oaut2registration.model.ServiceClient;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ServiceClientHistoryMappingDto {
    private ServiceClient client;
    private ServiceClient operator;
    private HistoryEvent event;
}
