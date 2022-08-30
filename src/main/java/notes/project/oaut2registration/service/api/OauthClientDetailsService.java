package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.dto.api.SystemRegistrationRequestDto;
import notes.project.oaut2registration.model.OauthClientDetails;

public interface OauthClientDetailsService {
    void registerSystemClient(SystemRegistrationRequestDto request);

    OauthClientDetails findByClientId(String clientId);
}
