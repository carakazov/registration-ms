package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.model.OauthClientHistory;
import notes.project.oaut2registration.repository.OauthClientHistoryRepository;
import notes.project.oaut2registration.service.api.impl.OauthClientHistoryServiceImpl;
import notes.project.oaut2registration.utils.DbUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OauthClientHistoryServiceImplTest {
    @Mock
    private OauthClientHistoryRepository repository;

    private OauthClientHistoryService service;

    @BeforeEach
    void init() {
        service = new OauthClientHistoryServiceImpl(repository);
    }

    @Test
    void saveSuccess() {
        OauthClientHistory request = DbUtils.oauthClientHistory().setId(null).setEventDate(null);

        when(repository.save(any())).thenReturn(DbUtils.oauthClientHistory());

        OauthClientHistory actual = service.save(request);

        assertEquals(DbUtils.oauthClientHistory(), actual);

        verify(repository).save(request);
    }
}
