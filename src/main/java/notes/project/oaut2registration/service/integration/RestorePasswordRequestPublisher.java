package notes.project.oaut2registration.service.integration;

import dto.integration.kafka.RestorePasswordRequestKafkaDto;

public interface RestorePasswordRequestPublisher {
    void publishMessage(RestorePasswordRequestKafkaDto message);
}
