package notes.project.oaut2registration.service;

import notes.project.oaut2registration.dto.SystemRegistrationRequestDto;

public interface OauthClientDetailsService {
    void registerSystemClient(SystemRegistrationRequestDto request);
}
