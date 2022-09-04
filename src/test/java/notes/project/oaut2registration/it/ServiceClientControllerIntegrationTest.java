package notes.project.oaut2registration.it;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.oaut2registration.controller.ServiceClientController;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.model.ServiceClientHistory;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.TestUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static notes.project.oaut2registration.utils.TestDataConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("it")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(AbstractIntegrationTest.IntegrationTestConfiguration.class)
class ServiceClientControllerIntegrationTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private ServiceClientController controller;

    @Inject
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @KafkaListener(topics = "additional.info.topic", groupId = "registration.ms.group")
    void listen(ConsumerRecord<?, byte[]> consumerRecord) throws IOException {
        String actualKafkaMessage = new String(consumerRecord.value());
        String expectedKafkaMessage = TestUtils.getClasspathResource(
            "integration/ServiceClientAdditionalInfoKafka.xml"
        );
        assertEquals(expectedKafkaMessage, actualKafkaMessage);
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

        ServiceClient serviceClient = getServiceClient();

        this.expectedKafkaMessage = TestUtils.getClasspathResource("integration/ServiceClientAdditionalInfoKafka.xml")
            .replace(REGISTRATION_DATE_PLACEHOLDER, serviceClient.getRegistrationDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .replace(CLIENT_EXTERNAL_ID_PLACEHOLDER, serviceClient.getExternalId().toString());

        assertNotNull(serviceClient);

        String expected = TestUtils.getClasspathResource("api/ServiceClientRegistrationResponse.json")
            .replace(REGISTRATION_DATE_PLACEHOLDER, serviceClient.getRegistrationDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .replace(CLIENT_EXTERNAL_ID_PLACEHOLDER, serviceClient.getExternalId().toString());

        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    void changeServiceClientRoles() throws Exception {
        setSecurityContext(Scope.CHANGE_ROLES);
        Role role = DbUtils.role().setRoleTitle(ROLE_TO_REMOVE);
        testEntityManager.merge(DbUtils.systemScope(Scope.CHANGE_ROLES));
        testEntityManager.merge(DbUtils.oauthClientDetails());
        testEntityManager.merge(role);
        testEntityManager.merge(DbUtils.role().setId(ID_2).setRoleTitle(ROLE_TO_ADD));
        List<Role> roles = new ArrayList<>();
        roles.add(DbUtils.role().setRoleTitle(ROLE_TO_REMOVE));
        testEntityManager.merge(DbUtils.serviceClient().setRoles(roles));

        ServiceClient serviceClient = getServiceClient();

        String actual = mockMvc.perform(MockMvcRequestBuilders.put("/client/98fcee4c-452a-4d58-9960-ddd6e0eb47dc/changeRoles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.getClasspathResource("api/ChangeServiceClientRolesRequest.json")))
            .andReturn().getResponse().getContentAsString();

        String expected = TestUtils.getClasspathResource("api/ChangeServiceClientRolesResponse.json")
            .replace(CLIENT_EXTERNAL_ID_PLACEHOLDER, serviceClient.getExternalId().toString());

        ServiceClientHistory serviceClientHistory = testEntityManager.getEntityManager().createQuery(
            "select history from service_client_history history where history.id = 1",
            ServiceClientHistory.class
        ).getSingleResult();

        assertNotNull(serviceClientHistory);

        JSONAssert.assertEquals(
            expected,
            actual,
            false
        );
    }

    @Test
    void changePasswordSuccess() throws Exception {
        setSecurityContext(Scope.CHANGE_PASSWORD);
        testEntityManager.merge(DbUtils.oauthClientDetails());
        testEntityManager.merge(DbUtils.role());
        ServiceClient operator = DbUtils.operator().setExternalId(OPERATOR_SERVICE_CLIENT_EXTERNAL_ID);
        testEntityManager.merge(DbUtils.serviceClient());
        testEntityManager.merge(operator);

        mockMvc.perform(MockMvcRequestBuilders.put("/client/changePassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.getClasspathResource("/api/ChangePasswordRequest.json")));

        ServiceClient serviceClient = getServiceClient();

        assertTrue(passwordEncoder.matches("some-new-password", serviceClient.getPassword()));
    }
}
