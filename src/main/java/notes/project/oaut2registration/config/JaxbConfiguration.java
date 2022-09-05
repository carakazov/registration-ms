package notes.project.oaut2registration.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import dto.integration.kafka.RestorePasswordRequestKafkaDto;
import dto.integration.kafka.ServiceClientAdditionalInfoKafkaDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaxbConfiguration {
    @Bean
    public Marshaller marshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(
            ServiceClientAdditionalInfoKafkaDto.class,
            RestorePasswordRequestKafkaDto.class
        );
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        return marshaller;
    }
}
