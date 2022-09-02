package notes.project.oaut2registration.dto.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на изменение ролей пользователя")
public class ChangeServiceClientRolesRequestDto {
    @ApiModelProperty(value = "Добавляемые роли пользователя")
    private List<String> rolesToAdd = new ArrayList<>();
    @ApiModelProperty(value = "Снимаемые с пользователя роли")
    private List<String> rolesToRemove = new ArrayList<>();
}
