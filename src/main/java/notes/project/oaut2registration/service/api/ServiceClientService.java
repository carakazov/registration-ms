package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.dto.api.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationResponseDto;

public interface ServiceClientService {
    ServiceClientRegistrationResponseDto registerServiceClient(ServiceClientRegistrationRequestDto request);
}
