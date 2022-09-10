package notes.project.oaut2registration.service.api;

import java.util.Collections;

import notes.project.oaut2registration.dto.OauthClientHistoryListResponseDto;
import notes.project.oaut2registration.model.OauthClientHistory;
import notes.project.oaut2registration.repository.OauthClientHistoryRepository;
import notes.project.oaut2registration.service.api.impl.OauthClientHistoryServiceImpl;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.mapper.OauthClientHistoryDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
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
        service = new OauthClientHistoryServiceImpl(
            repository,
            Mappers.getMapper(OauthClientHistoryDtoMapper.class)
        );
    }

    @Test
    void saveSuccess() {
        OauthClientHistory request = DbUtils.oauthClientHistory().setId(null).setEventDate(null);

        when(repository.save(any())).thenReturn(DbUtils.oauthClientHistory());

        OauthClientHistory actual = service.save(request);

        assertEquals(DbUtils.oauthClientHistory(), actual);

        verify(repository).save(request);
    }

    @Test
    void getHistorySuccess() {
        OauthClientHistoryListResponseDto expected = ApiUtils.oauthClientHistoryListResponseDto();

        when(repository.findAll()).thenReturn(Collections.singletonList(DbUtils.oauthClientHistory()));

        OauthClientHistoryListResponseDto actual = service.getHistory();

        assertEquals(expected, actual);

        verify(repository).findAll();
    }
}
