package notes.project.oaut2registration.service.api.impl;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.SystemRegistrationRequestDto;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.OauthClientHistory;
import notes.project.oaut2registration.model.OauthEvent;
import notes.project.oaut2registration.repository.OauthClientDetailsServiceRepository;
import notes.project.oaut2registration.service.api.OauthClientDetailsService;
import notes.project.oaut2registration.service.api.OauthClientHistoryService;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.mapper.CreateOauthClientDetailsMapper;
import notes.project.oaut2registration.utils.mapper.OauthClientHistoryMapper;
import notes.project.oaut2registration.utils.mapper.dto.OauthClientHistoryMappingDto;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.SystemRegistrationValidationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {
    private final OauthClientDetailsServiceRepository repository;
    private final CreateOauthClientDetailsMapper createOauthClientDetailsMapper;
    private final Validator<SystemRegistrationValidationDto> registerSystemValidator;
    private final PasswordEncoder passwordEncoder;
    private final OauthClientHistoryMapper oauthClientHistoryMapper;
    private final OauthClientHistoryService oauthClientHistoryService;
    private final AuthHelper authHelper;

    @Override
    @Transactional
    public void registerSystemClient(SystemRegistrationRequestDto request) {
        registerSystemValidator.validate(
            new SystemRegistrationValidationDto(request, repository.existsByClientId(request.getClientId()))
        );

        request.setClientSecret(passwordEncoder.encode(request.getClientSecret()));

        repository.save(createOauthClientDetailsMapper.from(request));
    }

    @Override
    public OauthClientDetails findByClientId(String clientId) {
        return repository.findByClientId(clientId)
            .orElseThrow(() -> new NotFoundException("Client with client id " + clientId + " does not exist."));
    }

    @Override
    @Transactional
    public void changeUserStatus(String clientId) {
        OauthClientDetails operator = findByClientId(authHelper.getClientId());
        OauthClientDetails client = findByClientId(clientId);

        client.setBlocked(!client.getBlocked());

        OauthClientHistory oauthClientHistory = oauthClientHistoryMapper.to(
            new OauthClientHistoryMappingDto(
                client,
                operator,
                Boolean.TRUE.equals(client.getBlocked()) ? OauthEvent.BLOCKED : OauthEvent.UNBLOCKED
            )
        );
        oauthClientHistoryService.save(oauthClientHistory);
    }
}
