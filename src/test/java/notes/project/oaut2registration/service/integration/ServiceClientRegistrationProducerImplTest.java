package notes.project.oaut2registration.service.integration;

import notes.project.oaut2registration.dto.api.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.dto.integration.ServiceClientAdditionalInfoKafkaDto;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.service.integration.impl.ServiceClientRegistrationProducerImpl;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.IntegrationUtils;
import notes.project.oaut2registration.utils.mapper.SendAdditionalInfoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ServiceClientRegistrationProducerImplTest {
    @Mock
    private ServiceClientRegistrationPublisher serviceClientRegistrationPublisher;

    private ServiceClientRegistrationProducer producer;

    @BeforeEach
    void init() {
        producer = new ServiceClientRegistrationProducerImpl(
            serviceClientRegistrationPublisher,
            Mappers.getMapper(SendAdditionalInfoMapper.class)
        );
    }

    @Test
    void produceMessageSuccess() {
        ServiceClientRegistrationRequestDto request = ApiUtils.serviceClientRegistrationRequestDto();
        ServiceClient client = DbUtils.serviceClient();
        ServiceClientAdditionalInfoKafkaDto message = IntegrationUtils.serviceClientAdditionalInfoKafkaDto();

        producer.produceMessage(request, client);

        verify(serviceClientRegistrationPublisher).publishMessage(message);
    }
}
