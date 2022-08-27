package notes.project.oaut2registration.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.CreateRoleRequestDto;
import notes.project.oaut2registration.service.RoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@Api(value = "Контроллер для управления ролями")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    @ApiOperation("Создание новой роли")
    public void createRole(@RequestBody CreateRoleRequestDto request) {
        roleService.createRole(request);
    }
}
