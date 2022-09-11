package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.dto.ServiceClientHistoryListResponseDto;
import notes.project.oaut2registration.model.ServiceClientHistory;

public interface ServiceClientHistoryService {
    ServiceClientHistory save(ServiceClientHistory serviceClientHistory);

    ServiceClientHistoryListResponseDto getHistory();
}
