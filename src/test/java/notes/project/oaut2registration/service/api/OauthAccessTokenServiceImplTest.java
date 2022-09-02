package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.repository.OauthAccessTokenRepository;
import notes.project.oaut2registration.service.api.impl.OauthAccessTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static notes.project.oaut2registration.utils.TestDataConstants.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OauthAccessTokenServiceImplTest {
    @Mock
    private OauthAccessTokenRepository repository;

    private OauthAccessTokenService service;

    @BeforeEach
    void init() {
        service = new OauthAccessTokenServiceImpl(repository);
    }

    @Test
    void deleteAccessTokenByClientIdAndUserNameWhenDeleted() {
        when(repository.existsByClientIdAndUserName(any(), any())).thenReturn(Boolean.TRUE);

        service.deleteAccessTokenByClientIdAndUserName(CLIENT_ID, USERNAME);

        verify(repository).existsByClientIdAndUserName(CLIENT_ID, USERNAME);
        verify(repository).deleteByClientIdAndUserName(CLIENT_ID, USERNAME);
    }

    @Test
    void deleteAccessTokenByClientIdAndUserNameWhenNotDeleted() {
        when(repository.existsByClientIdAndUserName(any(), any())).thenReturn(Boolean.FALSE);

        service.deleteAccessTokenByClientIdAndUserName(CLIENT_ID, USERNAME);

        verify(repository).existsByClientIdAndUserName(CLIENT_ID, USERNAME);
        verifyNoMoreInteractions(repository);
    }
}
