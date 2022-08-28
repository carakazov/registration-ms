package notes.project.oaut2registration.service.integration;

import notes.project.oaut2registration.dto.api.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.model.ServiceClient;

public interface ServiceClientRegistrationProducer {
    void produceMessage(ServiceClientRegistrationRequestDto request, ServiceClient client);
}
