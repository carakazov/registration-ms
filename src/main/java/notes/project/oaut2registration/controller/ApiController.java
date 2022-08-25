package notes.project.oaut2registration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/test")
    public String get() {
        return "test";
    }
}
