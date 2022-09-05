package notes.project.oaut2registration.service.integration.impl;

import dto.integration.kafka.RestorePasswordRequestKafkaDto;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.model.RestorePasswordStruct;
import notes.project.oaut2registration.service.integration.RestorePasswordRequestProducer;
import notes.project.oaut2registration.service.integration.RestorePasswordRequestPublisher;
import notes.project.oaut2registration.utils.mapper.SendRestorePasswordRequestMapper;
import notes.project.oaut2registration.utils.mapper.dto.RestorePasswordRequestKafkaMappingDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestorePasswordRequestProducerImpl implements RestorePasswordRequestProducer {
    private final RestorePasswordRequestPublisher restorePasswordRequestPublisher;
    private final SendRestorePasswordRequestMapper sendRestorePasswordRequestMapper;

    @Override
    public void produceMessage(RestorePasswordStruct restorePasswordStruct, String contact) {
        RestorePasswordRequestKafkaDto message = sendRestorePasswordRequestMapper.to(
            new RestorePasswordRequestKafkaMappingDto(
                restorePasswordStruct,
                contact
            )
        );
        restorePasswordRequestPublisher.publishMessage(message);
    }
}
