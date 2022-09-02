package notes.project.oaut2registration.dto.api;

import java.util.List;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Ответ на изменение роли")
public class ChangeServiceClientRolesResponseDto {
    @ApiModelProperty(value = "Внешний ID пользователя")
    private UUID userExternalId;
    @ApiModelProperty(value = "Список новых ролей пользователя")
    private List<String> newRoleTitles;
}
