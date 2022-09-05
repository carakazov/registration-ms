package notes.project.oaut2registration.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на инициализацию процесса восстановления пароля")
public class InitializePasswordRestoreRequestDto {
    @ApiModelProperty(value = "Идентификатор системы")
    @NotBlank
    @Length(min = 5, max = 255)
    private String clientId;
    @ApiModelProperty(value = "Контакт пользователя")
    @NotBlank
    @Length(min = 3, max = 1024)
    private String contact;
    @ApiModelProperty(value = "Новый пароль")
    @NotBlank
    @Length(min = 5, max = 255)
    private String newPassword;
}
