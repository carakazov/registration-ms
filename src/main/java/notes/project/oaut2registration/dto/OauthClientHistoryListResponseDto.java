package notes.project.oaut2registration.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "История изменений систем админом")
@NoArgsConstructor
@AllArgsConstructor
public class OauthClientHistoryListResponseDto {
    @ApiModelProperty(value = "История")
    private List<OauthClientHistoryDto> history;
}
