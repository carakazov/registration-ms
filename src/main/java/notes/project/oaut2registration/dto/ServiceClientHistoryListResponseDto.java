package notes.project.oaut2registration.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "История пользователей системы")
public class ServiceClientHistoryListResponseDto {
    private List<ServiceClientHistoryDto> history;
}
