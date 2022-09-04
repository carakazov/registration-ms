package notes.project.oaut2registration.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Ответ об успешной регистрации пользователя")
public class ServiceClientRegistrationResponseDto {
    @ApiModelProperty(value = "Логин зарегестрированного пользователя")
    private String username;
    @ApiModelProperty(value = "Дата регистрации")
    private LocalDateTime registrationDate;
    @ApiModelProperty(value = "Внешний ID созданного клиента")
    private UUID clientExternalId;
    @ApiModelProperty(value = "Роли пользователя")
    private List<String> userRoles;
}
