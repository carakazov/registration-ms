package notes.project.oaut2registration.service.integration.impl;

import java.io.StringWriter;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import dto.integration.kafka.ServiceClientAdditionalInfoKafkaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.project.oaut2registration.exception.ExceptionCode;
import notes.project.oaut2registration.service.integration.ServiceClientRegistrationPublisher;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceClientRegistrationPublisherImpl extends AbstractPublisher implements ServiceClientRegistrationPublisher {
    private static final String CLIENT_ADDITIONAL_INFO_BINDING = "registered-clients-topic";

    private final StreamBridge streamBridge;
    private final Marshaller marshaller;

    @Override
    public void publishMessage(ServiceClientAdditionalInfoKafkaDto message) {
        boolean sent = false;
        try {
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(message, stringWriter);
            sent = streamBridge.send(CLIENT_ADDITIONAL_INFO_BINDING, stringWriter.toString());
        } catch(RuntimeException exception) {
            sendFatalException(ExceptionCode.ADDITIONAL_INFO_SENDING_EXCEPTION, exception);
        } catch(JAXBException exception) {
            sendNotFatalException(ExceptionCode.ADDITIONAL_INFO_SENDING_EXCEPTION);
        }
        if(!sent) {
            sendNotFatalException(ExceptionCode.ADDITIONAL_INFO_SENDING_EXCEPTION);
        }
    }

}
