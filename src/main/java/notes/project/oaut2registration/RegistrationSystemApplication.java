package notes.project.oaut2registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing
public class RegistrationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistrationSystemApplication.class, args);
    }

}
