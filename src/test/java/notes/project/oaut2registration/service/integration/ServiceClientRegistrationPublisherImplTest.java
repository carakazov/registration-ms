package notes.project.oaut2registration.service.integration;

import notes.project.oaut2registration.dto.integration.ServiceClientAdditionalInfoKafkaDto;
import notes.project.oaut2registration.exception.RegistrationSystemException;
import notes.project.oaut2registration.service.integration.impl.ServiceClientRegistrationPublisherImpl;
import notes.project.oaut2registration.utils.IntegrationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static notes.project.oaut2registration.utils.TestDataConstants.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceClientRegistrationPublisherImplTest {
    @Mock
    private StreamBridge streamBridge;

    private ServiceClientRegistrationPublisher publisher;

    @BeforeEach
    void init() {
        publisher = new ServiceClientRegistrationPublisherImpl(streamBridge);
    }

    @Test
    void publishMessageSuccess() {
        ServiceClientAdditionalInfoKafkaDto request = IntegrationUtils.serviceClientAdditionalInfoKafkaDto();

        when(streamBridge.send(any(), any())).thenReturn(Boolean.TRUE);

        publisher.publishMessage(request);

        verify(streamBridge).send(CLIENT_ADDITIONAL_INFO_BINDING, request);
    }

    @Test
    void publishMessageWhenNotFatalException() {
        ServiceClientAdditionalInfoKafkaDto request = IntegrationUtils.serviceClientAdditionalInfoKafkaDto();

        when(streamBridge.send(any(), any())).thenReturn(Boolean.FALSE);

        assertThrows(
            RegistrationSystemException.class,
            () -> publisher.publishMessage(request)
        );

        verify(streamBridge).send(CLIENT_ADDITIONAL_INFO_BINDING, request);
    }

    @Test
    void publishMessageWhenFatalError() {
        ServiceClientAdditionalInfoKafkaDto request = IntegrationUtils.serviceClientAdditionalInfoKafkaDto();

        when(streamBridge.send(any(), any())).thenThrow(new RuntimeException());

        assertThrows(
            RegistrationSystemException.class,
            () -> publisher.publishMessage(request)
        );

        verify(streamBridge).send(CLIENT_ADDITIONAL_INFO_BINDING, request);
    }
}
