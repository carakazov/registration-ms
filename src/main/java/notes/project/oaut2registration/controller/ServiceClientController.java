package notes.project.oaut2registration.controller;

import java.util.UUID;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.api.ChangeServiceClientRolesRequestDto;
import notes.project.oaut2registration.dto.api.ChangeServiceClientRolesResponseDto;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationResponseDto;
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
}
