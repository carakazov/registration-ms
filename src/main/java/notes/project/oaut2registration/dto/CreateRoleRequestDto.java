package notes.project.oaut2registration.dto;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.Scope;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на создание роли")
public class CreateRoleRequestDto {
    @ApiModelProperty(value = "Название роли")
    @NotBlank
    @Min(3)
    @Max(25)
    private String roleTitle;

    @ApiModelProperty(value = "Список полномочий")
    @NotEmpty
    private List<Scope> scopes;
}
