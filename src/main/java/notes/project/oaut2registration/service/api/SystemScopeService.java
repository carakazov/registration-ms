package notes.project.oaut2registration.service.api;

import notes.project.oaut2registration.model.Scope;
import notes.project.oaut2registration.model.SystemScope;

public interface SystemScopeService {
    SystemScope findBySystemScope(Scope scope);
}