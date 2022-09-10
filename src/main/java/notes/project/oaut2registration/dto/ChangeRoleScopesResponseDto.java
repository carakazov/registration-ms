package notes.project.oaut2registration.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.Scope;

@Data
@Accessors(chain = true)
@ApiModel(value = "Ответ на изменение списка полномочий")
public class ChangeRoleScopesResponseDto {
    @ApiModelProperty(value = "Название роли")
    private String roleTitle;
    @ApiModelProperty(value = "Новые полномочия")
    private List<Scope> newScopesList;
}
