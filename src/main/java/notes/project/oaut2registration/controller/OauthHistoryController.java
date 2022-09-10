package notes.project.oaut2registration.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.OauthClientHistoryListResponseDto;
import notes.project.oaut2registration.service.api.OauthClientHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/history")
@Api("Контроллер по управлению историей сервиса")
public class OauthHistoryController {
    private final OauthClientHistoryService service;

    @GetMapping
    @ApiOperation("Запрос истории")
    public OauthClientHistoryListResponseDto getHistory() {
        return service.getHistory();
    }
}
