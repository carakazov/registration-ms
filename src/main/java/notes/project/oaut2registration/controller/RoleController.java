package notes.project.oaut2registration.controller;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.ChangeAssignedResourcesRequestDto;
import notes.project.oaut2registration.dto.ChangeRoleScopesResponseDto;
import notes.project.oaut2registration.dto.CreateRoleRequestDto;
import notes.project.oaut2registration.model.Scope;
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
    public void createRole(@RequestBody @Valid CreateRoleRequestDto request) {
        roleService.createRole(request);
    }

    @PutMapping("/{roleTitle}/changeStatus")
    @ApiOperation("Изменение статуса роли")
    public void changeRoleStatus(@PathVariable(name = "roleTitle") @ApiParam("Название роли") String roleTitle) {
        roleService.changeRoleStatus(roleTitle);
        serviceClientService.deleteTokenOfAllServiceClientOfRole(roleTitle);
    }

    @PutMapping("/{roleTitle}/changeScopes")
    public ChangeRoleScopesResponseDto changeRoleScopes(
        @PathVariable(name = "roleTitle") @ApiParam("Название роли") String roleTitle,
        @RequestBody @Valid ChangeAssignedResourcesRequestDto<Scope> request
        ) {
        return roleService.changeScopes(request, roleTitle);
    }
}
