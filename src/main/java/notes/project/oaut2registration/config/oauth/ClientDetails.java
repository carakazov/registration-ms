package notes.project.oaut2registration.config.oauth;

import java.util.stream.Collectors;

import lombok.Data;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.ServiceClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


public class ClientDetails extends User {
    public ClientDetails(ServiceClient client) {
        super(
            client.getUsername(),
            client.getPassword(),
            client.getRoles().stream()
                .map(item -> new SimpleGrantedAuthority(item.getRoleTitle()))
                .collect(Collectors.toList())
        );
    }
}
