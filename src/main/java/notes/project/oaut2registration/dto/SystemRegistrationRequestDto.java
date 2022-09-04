package notes.project.oaut2registration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel(description = "Запрос по регистрации новой системе в сервисе авторизации")
@Accessors(chain = true)
public class SystemRegistrationRequestDto {
    @NotBlank
    @Length(min = 5, max = 255)
    @ApiModelProperty(value = "Желаемый clientId клиентской системы")
    private String clientId;
    @NotBlank
    @Length(min = 5, max = 255)
    @ApiModelProperty(value = "Желаемый секрет клиента")
    private String clientSecret;
    @NotBlank
    @ApiModelProperty(value = "Пароль для регистрации")
    private String registrationPassword;
    @NotNull
    @ApiModelProperty(value = "Время жизни токена доступа")
    private Integer accessTokenValidity;
    @NotNull
    @ApiModelProperty(value = "Время жизни токен обновления")
    private Integer refreshTokenValidity;
    @NotNull
    @ApiModelProperty(value = "Признак разрешения анонимной регистрации")
    private Boolean anonRegistrationEnabled;
}
