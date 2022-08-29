package notes.project.oaut2registration.it;

import java.time.format.DateTimeFormatter;
import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.oaut2registration.controller.ServiceClientController;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("it")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(AbstractIntegrationTest.IntegrationTestConfiguration.class)
public class ServiceClientControllerIntegrationTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private ServiceClientController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void registerUserSuccess() throws Exception {
        testEntityManager.merge(DbUtils.systemScope());
        testEntityManager.merge(DbUtils.oauthClientDetails());
        testEntityManager.merge(DbUtils.role());

        setSecurityContext(Scope.ANON);

        String actual = mockMvc.perform(MockMvcRequestBuilders.post("/client")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.getClasspathResource("api/ServiceClientRegistrationRequest.json")))
            .andReturn().getResponse().getContentAsString();

        ServiceClient serviceClient = testEntityManager.getEntityManager().createQuery(
            "select service_client from service_clients service_client where service_client.id = 1",
            ServiceClient.class
        ).getSingleResult();

        assertNotNull(serviceClient);

        String expected = TestUtils.getClasspathResource("api/ServiceClientRegistrationResponse.json")
            .replace(REGISTRATION_DATE_PLACEHOLDER, serviceClient.getRegistrationDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .replace(CLIENT_EXTERNAL_ID_PLACEHOLDER, serviceClient.getExternalId().toString());

        JSONAssert.assertEquals(expected, actual, false);

        String expectedKafkaMessage = TestUtils.getClasspathResource("integration/ServiceClientAdditionalInfoKafka.json")
            .replace(REGISTRATION_DATE_PLACEHOLDER, serviceClient.getRegistrationDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .replace(CLIENT_EXTERNAL_ID_PLACEHOLDER, serviceClient.getExternalId().toString());

        JSONAssert.assertEquals(expectedKafkaMessage, actualKafkaMessage, false);
    }
}
