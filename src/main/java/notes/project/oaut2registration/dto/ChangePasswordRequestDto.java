package notes.project.oaut2registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на изменение запроса пользователя")
public class ChangePasswordRequestDto {
    @ApiModelProperty(value = "Старый пароль")
    private String oldPassword;
    @ApiModelProperty(value = "Новый пароль")
    private String newPassword;
}
