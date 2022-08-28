package notes.project.oaut2registration.service.api.impl;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.api.SystemRegistrationRequestDto;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.repository.OauthClientDetailsServiceRepository;
import notes.project.oaut2registration.service.api.OauthClientDetailsService;
import notes.project.oaut2registration.utils.mapper.CreateOauthClientDetailsMapper;
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
}
