package notes.project.oaut2registration.controller;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.SystemRegistrationRequestDto;
import notes.project.oaut2registration.service.api.OauthClientDetailsService;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{clientId}/changeStatus")
    @ApiOperation(value = "Изменения статуса клиента")
    public void changeOauthClientStatus(@PathVariable(name = "clientId") @ApiParam("Название системы") String clientId) {
        oauthClientDetailsService.changeUserStatus(clientId);
    }
}
