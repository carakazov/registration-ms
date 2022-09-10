package notes.project.oaut2registration.config.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.repository.ServiceClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userDetailsService")
@Slf4j
public class ClientDetailsService implements UserDetailsService {
    private final ServiceClientRepository serviceClientRepository;

    private final HttpServletRequest request;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            String clientId = request.getParameter("client_id");
            ServiceClient serviceClient = serviceClientRepository.findByUsernameAndOauthClientClientId(username, clientId);
            if(Boolean.FALSE.equals(serviceClient.getBlocked()) && Boolean.FALSE.equals(serviceClient.getOauthClient().getBlocked())) {
                return new ClientDetails(serviceClient);
            }
            throw new UsernameNotFoundException("User is banned");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }
}
