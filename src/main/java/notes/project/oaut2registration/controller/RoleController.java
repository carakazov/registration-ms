package notes.project.oaut2registration.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.CreateRoleRequestDto;
import notes.project.oaut2registration.service.api.RoleService;
import notes.project.oaut2registration.service.api.ServiceClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@Api(value = "Контроллер для управления ролями")
public class RoleController {
    private final RoleService roleService;
    private final ServiceClientService serviceClientService;

    @PostMapping
    @ApiOperation("Создание новой роли")
    public void createRole(@RequestBody CreateRoleRequestDto request) {
        roleService.createRole(request);
    }

    @PutMapping("/{roleTitle}/changeStatus")
    @ApiOperation("Изменение статуса роли")
    public void changeRoleStatus(@PathVariable(name = "roleTitle") @ApiParam("Название роли") String roleTitle) {
        roleService.changeRoleStatus(roleTitle);
        serviceClientService.deleteTokenOfAllServiceClientOfRole(roleTitle);
    }
}
