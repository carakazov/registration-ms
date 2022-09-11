package notes.project.oaut2registration.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("Пользователь системы")
public class ServiceClientDto extends OauthClientDto {
    @ApiModelProperty(value = "Внешний ID пользователя")
    private UUID externalId;
    @ApiModelProperty(value = "Дата регистрации")
    private LocalDateTime registrationDate;
    @ApiModelProperty(value = "Список ролей пользователя")
    private List<String> roles;
}
