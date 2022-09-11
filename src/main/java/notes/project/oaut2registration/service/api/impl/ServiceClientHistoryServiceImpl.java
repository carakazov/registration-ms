package notes.project.oaut2registration.service.api.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.ServiceClientHistoryListResponseDto;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.ServiceClientHistory;
import notes.project.oaut2registration.repository.ServiceClientHistoryRepository;
import notes.project.oaut2registration.service.api.OauthClientDetailsService;
import notes.project.oaut2registration.service.api.ServiceClientHistoryService;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.mapper.ServiceClientHistoryDtoMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceClientHistoryServiceImpl implements ServiceClientHistoryService {
    private final ServiceClientHistoryRepository repository;
    private final AuthHelper authHelper;
    private final OauthClientDetailsService oauthClientDetailsService;
    private final ServiceClientHistoryDtoMapper serviceClientHistoryDtoMapper;

    @Override
    @Transactional
    public ServiceClientHistory save(ServiceClientHistory serviceClientHistory) {
        return repository.save(serviceClientHistory);
    }

    @Override
    @Transactional
    public ServiceClientHistoryListResponseDto getHistory() {
        OauthClientDetails details = oauthClientDetailsService.findByClientId(authHelper.getClientId());
        List<ServiceClientHistory> history = repository.findAll().stream()
            .filter(item -> item.getClient().getOauthClient().equals(details) && item.getOperator().getOauthClient().equals(details))
            .collect(Collectors.toList());
        return new ServiceClientHistoryListResponseDto(serviceClientHistoryDtoMapper.to(history));
    }
}
