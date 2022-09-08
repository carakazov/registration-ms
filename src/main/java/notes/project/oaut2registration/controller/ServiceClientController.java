package notes.project.oaut2registration.controller;

import java.util.UUID;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.*;
import notes.project.oaut2registration.service.api.ServiceClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Api(value = "Контроллер для управления клиентами")
public class ServiceClientController {
    private final ServiceClientService serviceClientService;

    @PostMapping
    @ApiOperation(value = "Регистрация нового клиента")
    public ServiceClientRegistrationResponseDto registerServiceClient(@RequestBody @Valid ServiceClientRegistrationRequestDto request) {
        return serviceClientService.registerServiceClient(request);
    }

    @PutMapping("/{externalId}/changeRoles")
    @ApiOperation(value = "Смена ролей клиента")
    public ChangeServiceClientRolesResponseDto changeServiceClientRoles(
        @PathVariable(name = "externalId") @ApiParam(value = "Внешний ID пользователя") UUID externalId,
        @RequestBody @Valid ChangeServiceClientRolesRequestDto request
        ) {
        return serviceClientService.changeServiceClientRole(request, externalId);
    }

    @PutMapping("/changePassword")
    @ApiOperation(value = "Смена пароля")
    public void changePassword(@RequestBody @Valid ChangePasswordRequestDto request) {
        serviceClientService.changePassword(request);
    }

    @PostMapping("/restorePassword")
    @ApiOperation(value = "Запрос на создание заявки по восстановлению пароля")
    public void initializeRestorePasswordProcess(@RequestBody @Valid InitializePasswordRestoreRequestDto request) {
        serviceClientService.initializeRestorePasswordRequest(request);
    }

    @GetMapping("/{restoreCode}/{clientExternalId}")
    @ApiOperation(value = "Подтверждение нового пароля")
    public void restorePassword(
        @PathVariable(name = "restoreCode") @ApiParam(value = "Код восстановления") String restoreCode,
        @PathVariable(name = "clientExternalId") @ApiParam(value = "Внешний ID клиента") UUID clientExternalId
    ) {
        serviceClientService.restorePassword(clientExternalId, restoreCode);
    }

    @PutMapping("/{externalId}/changeStatus")
    @ApiOperation(value = "Бан/разбан пользователя")
    public void changeStatus(@PathVariable(name = "externalId") @ApiParam(value = "Внешний ID пользователя") UUID clientExternalId) {
        serviceClientService.changeUserStatus(clientExternalId);
    }
}
