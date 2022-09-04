package notes.project.oaut2registration.service.integration.impl;

import javax.transaction.Transactional;

import dto.integration.kafka.ServiceClientAdditionalInfoKafkaDto;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.service.integration.ServiceClientRegistrationProducer;
import notes.project.oaut2registration.service.integration.ServiceClientRegistrationPublisher;
import notes.project.oaut2registration.utils.mapper.SendAdditionalInfoMapper;
import notes.project.oaut2registration.utils.mapper.dto.SendAdditionalInfoMappingDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceClientRegistrationProducerImpl implements ServiceClientRegistrationProducer {
    private final ServiceClientRegistrationPublisher publisher;
    private final SendAdditionalInfoMapper sendAdditionalInfoMapper;

    @Override
    @Transactional
    public void produceMessage(ServiceClientRegistrationRequestDto request, ServiceClient client) {
        ServiceClientAdditionalInfoKafkaDto message = sendAdditionalInfoMapper.to(
            new SendAdditionalInfoMappingDto(
                request.getAdditionalInformation(),
                client
            )
        );
        publisher.publishMessage(message);
    }
}
