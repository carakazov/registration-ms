package notes.project.oaut2registration.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.HistoryEvent;

@Data
@Accessors(chain = true)
@ApiModel(description = "Единичная запись истории")
public class ServiceClientHistoryDto {
    @ApiModelProperty(value = "Пользователь")
    private String username;
    @ApiModelProperty(value = "Оператор")
    private String operator;
    @ApiModelProperty(value = "Дата операции")
    private LocalDateTime eventDate;
    @ApiModelProperty(value = "Операция")
    private HistoryEvent event;
}
