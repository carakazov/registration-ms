package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.dto.OauthClientHistoryListResponseDto;
import notes.project.oaut2registration.model.OauthClientHistory;

public interface OauthClientHistoryService {
    OauthClientHistory save(OauthClientHistory oauthClientHistory);

    OauthClientHistoryListResponseDto getHistory();
}
