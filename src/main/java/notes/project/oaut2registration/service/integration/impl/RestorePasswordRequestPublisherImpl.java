package notes.project.oaut2registration.service.integration.impl;

import java.io.StringWriter;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import dto.integration.kafka.RestorePasswordRequestKafkaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.service.integration.RestorePasswordRequestPublisher;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestorePasswordRequestPublisherImpl extends AbstractPublisher implements RestorePasswordRequestPublisher {
    private static final  String RESTORE_PASSWORD_REQUEST_TOPIC = "restore-password-topic";

    private final StreamBridge streamBridge;
    private final Marshaller marshaller;

    @Override
    public void publishMessage(RestorePasswordRequestKafkaDto message) {
        boolean sent = false;
        try {
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(message, stringWriter);
            sent = streamBridge.send(RESTORE_PASSWORD_REQUEST_TOPIC, stringWriter.toString());
        } catch(RuntimeException exception) {
            sendFatalException(ExceptionCode.RESTORE_PASSWORD_REQUEST_SENDING_EXCEPTION, exception);
        } catch(JAXBException exception) {
            sendNotFatalException(ExceptionCode.RESTORE_PASSWORD_REQUEST_SENDING_EXCEPTION);
        }
        if(!sent) {
            sendNotFatalException(ExceptionCode.RESTORE_PASSWORD_REQUEST_SENDING_EXCEPTION);
        }
    }
}
