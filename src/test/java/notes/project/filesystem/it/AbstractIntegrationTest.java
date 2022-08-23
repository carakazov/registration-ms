package notes.project.filesystem.it;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.FileResolution;
import notes.project.filesystem.utils.TestAsyncTaskExecutor;
import notes.project.filesystem.utils.TestDataConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ActiveProfiles;

import static notes.project.filesystem.utils.TestDataConstants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = AbstractIntegrationTest.IntegrationTestConfiguration.class)
@AutoConfigureJsonTesters
@ActiveProfiles("it")
public abstract class AbstractIntegrationTest {

    @Inject
    protected ApplicationProperties applicationProperties;
    @Inject
    protected ObjectMapper objectMapper;


    @ActiveProfiles("it")
    @TestConfiguration
    public static class IntegrationTestConfiguration {
        @Bean("asyncTaskExecutor")
        public TaskExecutor getTaskExecutor(EntityManager entityManager) {
            return new TestAsyncTaskExecutor(entityManager);
        }
    }

    @Inject
    protected TestEntityManager testEntityManager;

}
