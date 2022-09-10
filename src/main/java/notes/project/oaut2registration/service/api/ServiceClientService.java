package notes.project.oaut2registration.service.api;

import java.util.UUID;

import notes.project.oaut2registration.dto.*;
import notes.project.oaut2registration.model.ServiceClient;

public interface ServiceClientService {
    ServiceClientRegistrationResponseDto registerServiceClient(ServiceClientRegistrationRequestDto request);

    ChangeServiceClientRolesResponseDto changeServiceClientRole(ChangeAssignedResourcesRequestDto<String> request, UUID clientExternalId);

    ServiceClient findByExternalId(UUID externalId);

    void changePassword(ChangePasswordRequestDto request);

    void initializeRestorePasswordRequest(InitializePasswordRestoreRequestDto request);

    void restorePassword(UUID clientExternalId, String restoreCode);

    void changeUserStatus(UUID externalId);

    void deleteTokenOfAllServiceClientOfRole(String roleTitle);
}
