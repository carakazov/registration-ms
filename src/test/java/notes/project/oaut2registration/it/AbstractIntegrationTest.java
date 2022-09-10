package notes.project.oaut2registration.it;

import java.io.IOException;
import java.util.Collections;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import notes.project.oaut2registration.config.ApplicationProperties;
import notes.project.oaut2registration.config.oauth.dto.JwtDto;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.utils.TestAsyncTaskExecutor;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

import static notes.project.oaut2registration.utils.TestDataConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = AbstractIntegrationTest.IntegrationTestConfiguration.class)
@AutoConfigureJsonTesters
@ActiveProfiles("it")
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public abstract class AbstractIntegrationTest {
    protected static final String REGISTRATION_DATE_PLACEHOLDER = "<REGISTRATION_DATE_PLACEHOLDER>";
    protected static final String CLIENT_EXTERNAL_ID_PLACEHOLDER = "<CLIENT_EXTERNAL_ID_PLACEHOLDER>";
    protected String expectedKafkaMessage;

    @Inject
    protected ApplicationProperties applicationProperties;
    @Inject
    protected ObjectMapper objectMapper;
    @Inject
    protected WebApplicationContext context;
    @Inject
    protected ApplicationContext applicationContext;

    @ActiveProfiles("it")
    @TestConfiguration
    public static class IntegrationTestConfiguration {
        @Bean("asyncTaskExecutor")
        public TaskExecutor getTaskExecutor(EntityManager entityManager) {
            return new TestAsyncTaskExecutor(entityManager);
        }


        /*
        @Bean("dataSource")
        @Profile("it")
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
            dataSource.setUsername("sa");
            dataSource.setPassword("");
            return dataSource;
        }

         */

    }

    @Inject
    protected TestEntityManager testEntityManager;

    public static class IntegrationTestFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            filterChain.doFilter(request, response);
        }
    }

    protected void setSecurityContext(Scope scope) {
        setSecurityContext(scope.toString());
    }

    protected void setSecurityContext(String auth) {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                CLIENT_ID,
                new JwtDto().setUserName(USERNAME).setExternalId(OPERATOR_SERVICE_CLIENT_EXTERNAL_ID),
                Collections.singletonList(new SimpleGrantedAuthority(auth))
            )
        );
    }

    protected ServiceClient getServiceClient() {
       return testEntityManager.getEntityManager().createQuery(
            "select service_client from service_clients service_client where service_client.id = 1",
            ServiceClient.class).getSingleResult();
    }
}
