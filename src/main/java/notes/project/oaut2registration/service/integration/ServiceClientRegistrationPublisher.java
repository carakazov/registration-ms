package notes.project.oaut2registration.service.integration;

import notes.project.oaut2registration.dto.integration.ServiceClientAdditionalInfoKafkaDto;

public interface ServiceClientRegistrationPublisher {
    void publishMessage(ServiceClientAdditionalInfoKafkaDto message);
}
