package notes.project.oaut2registration.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import notes.project.oaut2registration.model.OauthEvent;

@Data
@Accessors(chain = true)
@ApiModel(value = "Единичная запись истории")
public class OauthClientHistoryDto {
    @ApiModelProperty(value = "Название системы")
    private String clientSystem;
    @ApiModelProperty(value = "Администратор")
    private String operator;
    @ApiModelProperty(value = "Событие истории")
    private OauthEvent event;
    @ApiModelProperty(value = "Дата и время")
    private LocalDateTime eventDate;
}
