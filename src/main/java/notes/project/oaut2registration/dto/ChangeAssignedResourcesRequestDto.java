package notes.project.oaut2registration.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на изменение связанных ресурсов")
public class ChangeAssignedResourcesRequestDto<T> {
    @ApiModelProperty(value = "Добавляемые ресурсы")
    private List<T> toAdd = new ArrayList<>();
    @ApiModelProperty(value = "Удаляемые ресурсы")
    private List<T> toRemove = new ArrayList<>();
}
