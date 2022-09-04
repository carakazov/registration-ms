package notes.project.oaut2registration.service.api;

import java.util.UUID;

import notes.project.oaut2registration.dto.ChangeServiceClientRolesRequestDto;
import notes.project.oaut2registration.dto.ChangeServiceClientRolesResponseDto;
import notes.project.oaut2registration.dto.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.dto.ServiceClientRegistrationResponseDto;
import notes.project.oaut2registration.model.ServiceClient;

public interface ServiceClientService {
    ServiceClientRegistrationResponseDto registerServiceClient(ServiceClientRegistrationRequestDto request);

    ChangeServiceClientRolesResponseDto changeServiceClientRole(ChangeServiceClientRolesRequestDto request, UUID clientExternalId);

    ServiceClient findByExternalId(UUID externalId);
}
