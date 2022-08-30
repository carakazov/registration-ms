package notes.project.oaut2registration.service.api.impl;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationResponseDto;
import notes.project.oaut2registration.model.OauthClientDetails;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.repository.ServiceClientRepository;
import notes.project.oaut2registration.service.api.OauthClientDetailsService;
import notes.project.oaut2registration.service.api.RoleService;
import notes.project.oaut2registration.service.api.ServiceClientService;
import notes.project.oaut2registration.service.integration.ServiceClientRegistrationProducer;
import notes.project.oaut2registration.utils.auth.AuthHelper;
import notes.project.oaut2registration.utils.mapper.ServiceClientRegistrationMapper;
import notes.project.oaut2registration.utils.mapper.dto.ServiceClientRegistrationMappingDto;
import notes.project.oaut2registration.utils.uuid.UuidHelper;
import notes.project.oaut2registration.utils.validation.Validator;
import notes.project.oaut2registration.utils.validation.dto.ServiceClientRegistrationValidationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceClientServiceImpl implements ServiceClientService {
    private final ServiceClientRepository repository;
    private final OauthClientDetailsService oauthClientDetailsService;
    private final RoleService roleService;
    private final Validator<ServiceClientRegistrationValidationDto> serviceClientRegistrationValidator;
    private final ServiceClientRegistrationMapper serviceClientRegistrationMapper;
    private final ServiceClientRegistrationProducer serviceClientRegistrationProducer;
    private final AuthHelper authHelper;
    private final PasswordEncoder passwordEncoder;
    private final UuidHelper uuidHelper;


    @Override
    @Transactional
    public ServiceClientRegistrationResponseDto registerServiceClient(ServiceClientRegistrationRequestDto request) {
        String currentScope = authHelper.getCurrentAuthority();
        OauthClientDetails details = oauthClientDetailsService.findByClientId(request.getAuthInformation().getClientId());
        serviceClientRegistrationValidator.validate(
            new ServiceClientRegistrationValidationDto(
                currentScope,
                details.getAnonRegistrationEnabled(),
                repository.existsByUsernameAndOauthClientClientId(request.getAuthInformation().getUsername(), details.getClientId()),
                request.getAdditionalInformation().getDateOfBirth()
            )
        );
        ServiceClient serviceClient = serviceClientRegistrationMapper.to(
            new ServiceClientRegistrationMappingDto(
                request.getAuthInformation().getUsername(),
                passwordEncoder.encode(request.getAuthInformation().getPassword()),
                uuidHelper.generateUuid(),
                details,
                request.getAuthInformation().getServiceClientRoles().stream()
                    .map(item -> roleService.findByClientIdAndRoleTitle(
                        request.getAuthInformation().getClientId(),
                        item
                    )).collect(Collectors.toList())
            )
        );

        serviceClient = repository.save(serviceClient);

        serviceClientRegistrationProducer.produceMessage(request, serviceClient);

        return serviceClientRegistrationMapper.from(request.getAuthInformation(), serviceClient);
    }
}
