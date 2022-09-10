package notes.project.oaut2registration.dto;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Дополнительная информация по пользователю")
@Valid
public class ServiceClientAdditionalInformationDto {
    @ApiModelProperty(value = "Имя")
    private String name;
    @ApiModelProperty(value = "Фамилия")
    private String surname;
    @ApiModelProperty(value = "Отчество")
    private String middleName;
    @Email
    @ApiModelProperty(value = "Электронная поста")
    private String email;
    @ApiModelProperty(value = "Дата рождения")
    private LocalDate dateOfBirth;
    @ApiModelProperty(value = "Любая другая информация по пользователю в формате key -> value")
    private List<ServiceClientAdditionalInfoRecordDto> additionalInfo;
}
