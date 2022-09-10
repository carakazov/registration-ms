package notes.project.oaut2registration.it;

import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.oaut2registration.controller.OauthClientDetailsController;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.OauthClientHistory;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("it")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(AbstractIntegrationTest.IntegrationTestConfiguration.class)
class OauthClientDetailsControllerIntegrationTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private OauthClientDetailsController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void registerSystemClientSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register-system-client")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.getClasspathResource("/api/RegisterSystemClient.json")))
            .andExpect(status().isOk());

        OauthClientDetails details = testEntityManager.getEntityManager().createQuery(
            "select details from oauth_client_details details where details.clientId = :clientId",
            OauthClientDetails.class
        ).setParameter("clientId", "my-client-id").getSingleResult();

        assertNotNull(details);
    }

    @Test
    void changeOauthClientStatusSuccess() throws Exception {
        setSecurityContext("OAUTH_ADMIN");
        testEntityManager.merge(DbUtils.oauthClientDetails());
        testEntityManager.merge(DbUtils.oauthClientDetailsOperator());

        mockMvc.perform(MockMvcRequestBuilders.put("/auth/client id/changeStatus"))
            .andExpect(status().isOk());

        OauthClientDetails details = testEntityManager.getEntityManager().createQuery(
            "select details from oauth_client_details details where details.clientId = 'client id'",
            OauthClientDetails.class
        ).getSingleResult();

        assertTrue(details.getBlocked());

        OauthClientHistory oauthClientHistory = testEntityManager.getEntityManager().createQuery(
            "select history from oauth_client_history history where history.id = 1",
            OauthClientHistory.class
        ).getSingleResult();

        assertNotNull(oauthClientHistory);
    }
}
