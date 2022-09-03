package notes.project.oaut2registration.service.integration;

import java.io.StringWriter;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import dto.integration.kafka.ServiceClientAdditionalInfoKafkaDto;
import notes.project.oaut2registration.exception.RegistrationSystemException;
import notes.project.oaut2registration.service.integration.impl.ServiceClientRegistrationPublisherImpl;
import notes.project.oaut2registration.utils.IntegrationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;

import static notes.project.oaut2registration.utils.TestDataConstants.CLIENT_ADDITIONAL_INFO_BINDING;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceClientRegistrationPublisherImplTest {
    @Mock
    private StreamBridge streamBridge;
    @Mock
    private Marshaller marshaller;

    private ServiceClientRegistrationPublisher publisher;

    @BeforeEach
    void init() {
        publisher = new ServiceClientRegistrationPublisherImpl(streamBridge, marshaller);
    }

    @Test
    void publishMessageSuccess() throws JAXBException {
        ServiceClientAdditionalInfoKafkaDto request = IntegrationUtils.serviceClientAdditionalInfoKafkaDto();

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(request, stringWriter);
        String expectedString = stringWriter.toString();

        when(streamBridge.send(any(), any())).thenReturn(Boolean.TRUE);

        publisher.publishMessage(request);

        verify(streamBridge).send(CLIENT_ADDITIONAL_INFO_BINDING, expectedString);
    }

    @Test
    void publishMessageWhenNotFatalException() throws JAXBException {
        ServiceClientAdditionalInfoKafkaDto request = IntegrationUtils.serviceClientAdditionalInfoKafkaDto();

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(request, stringWriter);
        String expectedString = stringWriter.toString();

        when(streamBridge.send(any(), any())).thenReturn(Boolean.FALSE);

        assertThrows(
            RegistrationSystemException.class,
            () -> publisher.publishMessage(request)
        );

        verify(streamBridge).send(CLIENT_ADDITIONAL_INFO_BINDING, expectedString);
    }

    @Test
    void publishMessageWhenFatalError() throws JAXBException {
        ServiceClientAdditionalInfoKafkaDto request = IntegrationUtils.serviceClientAdditionalInfoKafkaDto();

        when(streamBridge.send(any(), any())).thenThrow(new RuntimeException());

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(request, stringWriter);
        String expectedString = stringWriter.toString();

        assertThrows(
            RegistrationSystemException.class,
            () -> publisher.publishMessage(request)
        );

        verify(streamBridge).send(CLIENT_ADDITIONAL_INFO_BINDING, expectedString);
    }
}
