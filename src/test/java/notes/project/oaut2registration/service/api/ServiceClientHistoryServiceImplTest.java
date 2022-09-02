package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.model.ServiceClientHistory;
import notes.project.oaut2registration.repository.ServiceClientHistoryRepository;
import notes.project.oaut2registration.service.api.impl.ServiceClientHistoryServiceImpl;
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
class ServiceClientHistoryServiceImplTest {
    @Mock
    private ServiceClientHistoryRepository repository;

    private ServiceClientHistoryService service;

    @BeforeEach
    void init() {
        service = new ServiceClientHistoryServiceImpl(repository);
    }

    @Test
    void saveSuccess() {
       ServiceClientHistory request = DbUtils.serviceClientHistory().setId(null).setEventDate(null);
       ServiceClientHistory expected = DbUtils.serviceClientHistory();

       when(repository.save(any())).thenReturn(expected);

       ServiceClientHistory actual = service.save(request);

       assertEquals(expected, actual);

       verify(repository).save(request);
    }
}
