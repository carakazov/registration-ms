package notes.project.oaut2registration.dto;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@ApiModel(description = "Валидационная ошибка при запросе")
public class ValidationErrorDto {
    @ApiModelProperty(value = "Список ошибок", required = true)
    private List<ErrorDto> validationErrors;
}
