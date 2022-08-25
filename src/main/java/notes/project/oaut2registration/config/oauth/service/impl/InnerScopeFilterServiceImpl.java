package notes.project.oaut2registration.config.oauth.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.config.oauth.dto.JwtDto;
import notes.project.oaut2registration.config.oauth.service.InnerScopeFilterService;
import notes.project.oaut2registration.model.Role;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.ServiceClient;
import notes.project.oaut2registration.repository.ServiceClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InnerScopeFilterServiceImpl implements InnerScopeFilterService {
    private final ServiceClientRepository serviceClientRepository;

    @Override
    @Transactional
    public List<String> extractScopes(JwtDto jwtDto) {
        ServiceClient serviceClient = serviceClientRepository.findByUsernameAndOauthClientClientId(
            jwtDto.getUserName(),
            jwtDto.getClientId()
        );
        List<Role> roles = serviceClient.getRoles();
        List<String> scopes = new ArrayList<>();
        for(Role role : roles) {
            role.getScopes().forEach(item -> scopes.add(item.getSystemScope().toString()));
        }
        return scopes;
    }
}
