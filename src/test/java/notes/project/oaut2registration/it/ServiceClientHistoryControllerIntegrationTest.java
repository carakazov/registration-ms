package notes.project.oaut2registration.it;

import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.oaut2registration.controller.ServiceClientHistoryController;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.ServiceClientHistory;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("it")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(AbstractIntegrationTest.IntegrationTestConfiguration.class)
class ServiceClientHistoryControllerIntegrationTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private ServiceClientHistoryController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void getHistorySuccess() throws Exception {
        setSecurityContext(Scope.GET_SERVICE_CLIENT_HISTORY);
        testEntityManager.merge(DbUtils.oauthClientDetails());
        testEntityManager.merge(DbUtils.role());
        testEntityManager.merge(DbUtils.serviceClient());
        testEntityManager.merge(DbUtils.operator());
        testEntityManager.merge(DbUtils.serviceClientHistory());

        ServiceClientHistory serviceClientHistory = testEntityManager.getEntityManager().createQuery(
            "select history from service_client_history history where history.id = 1",
            ServiceClientHistory.class
        ).getSingleResult();

        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/history"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        String expected = TestUtils.getClasspathResource("/api/ServiceClientHistoryListResponse.json");

        JSONAssert.assertEquals(expected, actual, false);
    }
}
