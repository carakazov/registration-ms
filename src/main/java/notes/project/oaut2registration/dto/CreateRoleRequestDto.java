package notes.project.oaut2registration.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.Scope;
import org.hibernate.validator.constraints.Length;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на создание роли")
public class CreateRoleRequestDto {
    @ApiModelProperty(value = "Название роли")
    @NotBlank
    @Length(min = 3, max = 25)
    private String roleTitle;

    @ApiModelProperty(value = "Список полномочий")
    @NotEmpty
    private List<Scope> scopes;
}
