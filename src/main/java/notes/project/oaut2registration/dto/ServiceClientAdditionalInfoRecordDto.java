package notes.project.oaut2registration.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Единичная дополнительная запись")
public class ServiceClientAdditionalInfoRecordDto {
    @ApiModelProperty(value = "Название поля")
    @NotBlank
    private String field;
    @ApiModelProperty(value = "Значение")
    @NotBlank
    private Object value;
}
