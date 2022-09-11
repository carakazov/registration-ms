package notes.project.oaut2registration.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.ServiceClientHistoryListResponseDto;
import notes.project.oaut2registration.service.api.ServiceClientHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api("Контроллер для запроса истории")
@RequestMapping("/history")
public class ServiceClientHistoryController {
    private final ServiceClientHistoryService service;

    @GetMapping
    @ApiOperation("Запрос истории")
    public ServiceClientHistoryListResponseDto getHistory() {
        return service.getHistory();
    }
}
