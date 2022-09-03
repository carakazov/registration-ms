package notes.project.oaut2registration.service.api.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.SystemScope;
import notes.project.oaut2registration.repository.SystemScopeRepository;
import notes.project.oaut2registration.service.api.SystemScopeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemScopeServiceImpl implements SystemScopeService {
    private final SystemScopeRepository repository;

    @Override
    public SystemScope findBySystemScope(Scope scope) {
        return repository.findBySystemScope(scope);
    }
}
