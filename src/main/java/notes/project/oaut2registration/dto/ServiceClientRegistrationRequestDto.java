package notes.project.oaut2registration.dto;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на регистрацию пользователя")
@Valid
public class ServiceClientRegistrationRequestDto {
    @ApiModelProperty(value = "Информация по аутентификации пользователя")
    @NotNull
    private ServiceClientAuthInformationDto authInformation;
    @ApiModelProperty(value = "Дополнительная информация по пользователю")
    @NotNull
    private ServiceClientAdditionalInformationDto additionalInformation;
}
