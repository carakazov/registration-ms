package notes.project.oaut2registration.service.integration;

import java.io.StringWriter;
import javax.xml.bind.Marshaller;

import dto.integration.kafka.RestorePasswordRequestKafkaDto;
import notes.project.oaut2registration.exception.RegistrationSystemException;
import notes.project.oaut2registration.service.integration.impl.RestorePasswordRequestPublisherImpl;
import notes.project.oaut2registration.utils.IntegrationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

import static notes.project.oaut2registration.utils.TestDataConstants.RESTORE_PASSWORD_TOPIC;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestorePasswordRequestPublisherImplTest {
    @Mock
    private StreamBridge streamBridge;
    @Mock
    private Marshaller marshaller;

    private RestorePasswordRequestPublisher publisher;

    @BeforeEach
    void init() {
        publisher = new RestorePasswordRequestPublisherImpl(streamBridge, marshaller);
    }

    @Test
    void publishMessageSuccess() throws Exception {
        RestorePasswordRequestKafkaDto request = IntegrationUtils.restorePasswordRequestKafkaDto();

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(request, stringWriter);
        String expectedMessage = stringWriter.toString();

        when(streamBridge.send(any(), any())).thenReturn(Boolean.TRUE);

        publisher.publishMessage(request);

        verify(streamBridge).send(RESTORE_PASSWORD_TOPIC, expectedMessage);
    }

    @Test
    void publishMessageWhenNotFatalError() throws Exception {
        RestorePasswordRequestKafkaDto request = IntegrationUtils.restorePasswordRequestKafkaDto();

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(request, stringWriter);
        String expectedMessage = stringWriter.toString();

        when(streamBridge.send(any(), any())).thenReturn(Boolean.FALSE);

        assertThrows(
            RegistrationSystemException.class,
            () -> publisher.publishMessage(request)
        );

        verify(streamBridge).send(RESTORE_PASSWORD_TOPIC, expectedMessage);
    }

    @Test
    void publishMessageWhenFatalError()  {
        RestorePasswordRequestKafkaDto request = IntegrationUtils.restorePasswordRequestKafkaDto();

        assertThrows(
            RegistrationSystemException.class,
            () -> publisher.publishMessage(request)
        );
    }
}
