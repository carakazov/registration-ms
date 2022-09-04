package notes.project.oaut2registration.utils.auth;

import java.util.UUID;

public interface AuthHelper {
    String getClientId();
    String getCurrentUserName();
    String getCurrentAuthority();
    UUID getCurrentExternalId();
}
