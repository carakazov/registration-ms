package notes.project.oaut2registration.service.api;

import java.util.Optional;

import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.RestorePasswordStruct;
import notes.project.oaut2registration.repository.RestorePasswordStructRepository;
import notes.project.oaut2registration.service.api.impl.RestorePasswordStructServiceImpl;
import notes.project.oaut2registration.utils.DbUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static notes.project.oaut2registration.utils.TestDataConstants.RESTORE_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestorePasswordStructServiceImplTest {
    @Mock
    private RestorePasswordStructRepository repository;

    private RestorePasswordStructService service;

    @BeforeEach
    void init() {
        service = new RestorePasswordStructServiceImpl(repository);
    }

    @Test
    void saveSuccess() {
        RestorePasswordStruct expected = DbUtils.restorePasswordStruct();
        when(repository.save(any())).thenReturn(expected);

        RestorePasswordStruct actual = service.save(expected.setId(null));

        assertEquals(expected, actual);

        verify(repository).save(expected.setId(null));
    }

    @Test
    void findByRestoreCodeSuccess() {
        RestorePasswordStruct expected = DbUtils.restorePasswordStruct();

        when(repository.findByRestoreCode(any())).thenReturn(Optional.of(expected));

        RestorePasswordStruct actual = service.findByRestoreCode(RESTORE_CODE);

        assertEquals(expected, actual);

        verify(repository).findByRestoreCode(RESTORE_CODE);
    }

    @Test
    void findByRestoreCodeWhenNotFound() {
        when(repository.findByRestoreCode(any())).thenThrow(new NotFoundException("test"));

        assertThrows(
            NotFoundException.class,
            () -> service.findByRestoreCode(RESTORE_CODE)
        );

        verify(repository).findByRestoreCode(RESTORE_CODE);
    }

    @Test
    void changeStructInProcessStatus() {
        RestorePasswordStruct expected = DbUtils.restorePasswordStruct().setInProcess(Boolean.FALSE);

        RestorePasswordStruct actual = service.changeStructInProcessStatus(DbUtils.restorePasswordStruct());

        assertEquals(expected, actual);
    }
}
