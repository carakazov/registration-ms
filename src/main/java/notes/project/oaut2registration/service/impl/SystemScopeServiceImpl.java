package notes.project.oaut2registration.service.impl;

import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.exception.NotFoundException;
import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.SystemScope;
import notes.project.oaut2registration.repository.SystemScopeRepository;
import notes.project.oaut2registration.service.SystemScopeService;
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
