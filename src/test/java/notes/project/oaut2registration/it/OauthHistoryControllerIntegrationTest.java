package notes.project.oaut2registration.it;

import java.time.format.DateTimeFormatter;
import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.oaut2registration.controller.OauthHistoryController;
import notes.project.oaut2registration.model.OauthClientHistory;
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
class OauthHistoryControllerIntegrationTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private OauthHistoryController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void getHistorySuccess() throws Exception {
        testEntityManager.merge(DbUtils.oauthClientDetails());
        testEntityManager.merge(DbUtils.oauthClientDetailsOperator());
        testEntityManager.merge(DbUtils.oauthClientHistory());

        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/oauth/history"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        OauthClientHistory history = testEntityManager.getEntityManager().createQuery(
            "select history from oauth_client_history history where history.id = 1",
            OauthClientHistory.class
        ).getSingleResult();

        String expected = TestUtils.getClasspathResource("/api/OauthHistoryListResponse.json")
            .replace("<EVENT_DATE>", history.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")));

        JSONAssert.assertEquals(expected, actual, true);
    }
}
