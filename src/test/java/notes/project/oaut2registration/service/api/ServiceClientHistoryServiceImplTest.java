package notes.project.oaut2registration.service.api;

import java.util.Collections;

import notes.project.oaut2registration.dto.ServiceClientHistoryListResponseDto;
import notes.project.oaut2registration.model.ServiceClientHistory;
import notes.project.oaut2registration.repository.ServiceClientHistoryRepository;
import notes.project.oaut2registration.service.api.impl.ServiceClientHistoryServiceImpl;
import notes.project.oaut2registration.utils.ApiUtils;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.mapper.ServiceClientHistoryDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static notes.project.oaut2registration.utils.TestDataConstants.CLIENT_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceClientHistoryServiceImplTest {
    @Mock
    private ServiceClientHistoryRepository repository;
    @Mock
    private AuthHelper authHelper;
    @Mock
    private OauthClientDetailsService oauthClientDetailsService;

    private ServiceClientHistoryService service;

    @BeforeEach
    void init() {
        service = new ServiceClientHistoryServiceImpl(
            repository,
            authHelper,
            oauthClientDetailsService,
            Mappers.getMapper(ServiceClientHistoryDtoMapper.class)
        );
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

    @Test
    void getHistorySuccess() {
        when(authHelper.getClientId()).thenReturn(CLIENT_ID);
        when(oauthClientDetailsService.findByClientId(any())).thenReturn(DbUtils.oauthClientDetails());
        when(repository.findAll()).thenReturn(Collections.singletonList(DbUtils.serviceClientHistory()));

        ServiceClientHistoryListResponseDto expected = ApiUtils.serviceClientHistoryListResponseDto();

        ServiceClientHistoryListResponseDto actual = service.getHistory();

        assertEquals(expected, actual);

        verify(authHelper).getClientId();
        verify(oauthClientDetailsService).findByClientId(CLIENT_ID);
        verify(repository).findAll();
    }
}
