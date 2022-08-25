package notes.project.oaut2registration.config.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.repository.ServiceClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userDetailsService")
public class ClientDetailsService implements UserDetailsService {
    private final ServiceClientRepository serviceClientRepository;

    private final HttpServletRequest request;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            String clientId = request.getParameter("client_id");
            ServiceClient serviceClient = serviceClientRepository.findByUsernameAndOauthClientClientId(username, clientId);
            ClientDetails clientDetails = new ClientDetails(serviceClient);
            return clientDetails;
        } catch(Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }
}
