package notes.project.oaut2registration.service.api;

import java.util.UUID;

import notes.project.oaut2registration.dto.*;
import notes.project.oaut2registration.model.ServiceClient;

public interface ServiceClientService {
    ServiceClientRegistrationResponseDto registerServiceClient(ServiceClientRegistrationRequestDto request);

    ChangeServiceClientRolesResponseDto changeServiceClientRole(ChangeServiceClientRolesRequestDto request, UUID clientExternalId);

    ServiceClient findByExternalId(UUID externalId);

    void changePassword(ChangePasswordRequestDto request);
}
