package notes.project.oaut2registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "Запись о клиенте сервиса")
public class OauthClientDto {
    @ApiModelProperty(value = "Юзернейм")
    private String username;
    @ApiModelProperty(value = "Статус")
    private Boolean blocked;
}
