package notes.project.oaut2registration.controller;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.SystemRegistrationRequestDto;
import notes.project.oaut2registration.service.api.OauthClientDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Api(value = "Контроллер для управления аккаунтами внешних систем")
public class OauthClientDetailsController {
    private final OauthClientDetailsService oauthClientDetailsService;

    @PostMapping("/register-system-client")
    @ApiOperation(value = "Регистрация новой системы-клиента")
    public void registerSystemClient(@RequestBody @Valid SystemRegistrationRequestDto request) {
        oauthClientDetailsService.registerSystemClient(request);
    }
}
