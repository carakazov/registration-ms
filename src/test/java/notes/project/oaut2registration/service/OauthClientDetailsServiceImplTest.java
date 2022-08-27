package notes.project.oaut2registration.service;

import java.util.Optional;
import javax.validation.Valid;

import notes.project.oaut2registration.dto.SystemRegistrationRequestDto;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.repository.OauthClientDetailsServiceRepository;
import notes.project.oaut2registration.service.impl.OauthClientDetailsServiceImpl;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.mapper.CreateOauthClientDetailsMapper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.SystemRegistrationValidationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static notes.project.oaut2registration.utils.TestDataConstants.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OauthClientDetailsServiceImplTest {
    @Mock
    private OauthClientDetailsServiceRepository repository;
    @Mock
    private Validator<SystemRegistrationValidationDto> registerSystemValidator;
    @Mock
    private PasswordEncoder passwordEncoder;

    private OauthClientDetailsService service;

    @BeforeEach
    void init() {
        service = new OauthClientDetailsServiceImpl(
            repository,
            Mappers.getMapper(CreateOauthClientDetailsMapper.class),
            registerSystemValidator,
            passwordEncoder
        );
    }

    @Test
    void registerSystemClientSuccess() {
        SystemRegistrationRequestDto request = ApiUtils.systemRegistrationRequestDto();
        OauthClientDetails details = DbUtils.oauthClientDetails();

        when(repository.existsByClientId(any())).thenReturn(Boolean.FALSE);
        when(passwordEncoder.encode(any())).thenReturn(PASSWORD_ENCODED);

        service.registerSystemClient(request);

        verify(repository).existsByClientId(request.getClientId());
        verify(passwordEncoder).encode(CLIENT_SECRET);
        verify(repository).save(details);
        verifyNoMoreInteractions(passwordEncoder, repository);
    }

    @Test
    void findByClientIdSuccess() {
        Optional<OauthClientDetails> expected = Optional.of(DbUtils.oauthClientDetails());

        when(repository.findByClientId(any())).thenReturn(expected);

        OauthClientDetails actual = service.findByClientId(CLIENT_ID);

        assertEquals(expected.get(), actual);

        verify(repository).findByClientId(expected.get().getClientId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findByClientIdWhenNotFound() {
        Optional<OauthClientDetails> expected = Optional.empty();

        when(repository.findByClientId(any())).thenReturn(expected);

        assertThrows(
            NotFoundException.class,
            () -> service.findByClientId(CLIENT_ID)
        );

        verify(repository).findByClientId(CLIENT_ID);
        verifyNoMoreInteractions(repository);
    }
}
