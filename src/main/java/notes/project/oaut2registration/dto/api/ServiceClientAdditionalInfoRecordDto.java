package notes.project.oaut2registration.dto.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Единичная дополнительная запись")
public class ServiceClientAdditionalInfoRecordDto {
    @ApiModelProperty(value = "Название поля")
    private String field;
    @ApiModelProperty(value = "Значение")
    private Object value;
}
