package notes.project.oaut2registration.dto;

import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на изменение запроса пользователя")
public class ChangePasswordRequestDto {
    @ApiModelProperty(value = "Внешний ID пользователя. Передается, если пароль меняет не сам пользователь")
    private UUID externalId;
    @ApiModelProperty(value = "Старый пароль")
    @Length(min = 5, max = 255)
    private String oldPassword;
    @ApiModelProperty(value = "Новый пароль")
    @Length(min = 5, max = 255)
    private String newPassword;
}
