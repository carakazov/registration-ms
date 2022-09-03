package notes.project.oaut2registration.service.integration;

import dto.integration.kafka.ServiceClientAdditionalInfoKafkaDto;

public interface ServiceClientRegistrationPublisher {
    void publishMessage(ServiceClientAdditionalInfoKafkaDto message);
}
