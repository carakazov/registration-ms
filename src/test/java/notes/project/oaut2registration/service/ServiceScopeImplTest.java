package notes.project.oaut2registration.service;

import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.SystemScope;
import notes.project.oaut2registration.repository.SystemScopeRepository;
import notes.project.oaut2registration.service.api.SystemScopeService;
import notes.project.oaut2registration.service.api.impl.SystemScopeServiceImpl;
import notes.project.oaut2registration.utils.DbUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceScopeImplTest {
    @Mock
    private SystemScopeRepository repository;

    private SystemScopeService service;

    @BeforeEach
    void init() {
        service = new SystemScopeServiceImpl(repository);
    }

    @Test
    void findBySystemScopeSuccess() {
        SystemScope expected = DbUtils.systemScope();

        when(repository.findBySystemScope(any())).thenReturn(expected);

        SystemScope actual = service.findBySystemScope(Scope.ANON);

        assertEquals(expected, actual);

        verify(repository).findBySystemScope(Scope.ANON);
        verifyNoMoreInteractions(repository);
    }
}
