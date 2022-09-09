package notes.project.oaut2registration.it;

import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.oaut2registration.controller.RoleController;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.Scope;
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
class RoleControllerIntegrationTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private RoleController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void createRoleSuccess() throws Exception {
        setSecurityContext(Scope.CREATE_ROLE);
        testEntityManager.merge(DbUtils.oauthClientDetails());

        mockMvc.perform(MockMvcRequestBuilders.post("/role")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.getClasspathResource("/api/CreateRoleRequest.json")))
            .andExpect(status().isOk());

        Role role = testEntityManager.getEntityManager().createQuery(
            "select role from roles role where role.id = 1", Role.class
        ).getSingleResult();

        assertNotNull(role);
    }


    @Test
    void changeRoleStatusSuccess() throws Exception {
        setSecurityContext(Scope.CHANGE_ROLE_STATUS);
        testEntityManager.merge(DbUtils.oauthClientDetails());
        testEntityManager.merge(DbUtils.role());
        testEntityManager.merge(DbUtils.serviceClient());

        mockMvc.perform(MockMvcRequestBuilders.put("/role/TEST_ROLE/changeStatus"))
            .andExpect(status().isOk());

        Role role = testEntityManager.getEntityManager().createQuery(
            "select role from roles role where role.id = 1",
            Role.class
        ).getSingleResult();

        assertTrue(role.getBlocked());
    }
}
