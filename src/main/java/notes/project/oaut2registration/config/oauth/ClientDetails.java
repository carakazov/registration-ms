package notes.project.oaut2registration.config.oauth;

import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Getter;
import notes.project.oaut2registration.model.ServiceClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class ClientDetails extends User {
    @Getter
    private final UUID externalId;
    public ClientDetails(ServiceClient client) {
        super(
            client.getUsername(),
            client.getPassword(),
            client.getRoles().stream()
                .map(item -> new SimpleGrantedAuthority(item.getRoleTitle()))
                .collect(Collectors.toList())
        );
        this.externalId = client.getExternalId();
    }
}
