package notes.project.oaut2registration.dto.api;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@Accessors(chain = true)
@ApiModel(description = "Информация по пользователю, нужная для аутентификации")
public class ServiceClientAuthInformationDto {
    @NotBlank
    @ApiModelProperty(value = "Идентификатор системы")
    @Length(min = 5, max = 255)
    private String clientId;
    @NotBlank
    @ApiModelProperty(value = "Логин пользователя")
    @Length(min = 5, max = 255)
    private String username;
    @NotBlank
    @ApiModelProperty(value = "Пароль пользователя (в незашифрованном виде)")
    @Length(min = 5, max = 255)
    private String password;
    @NotEmpty
    @ApiModelProperty(value = "Список ролей пользователя")
    private List<String> serviceClientRoles;
}
