package notes.project.oaut2registration.controller;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationRequestDto;
import notes.project.oaut2registration.dto.api.ServiceClientRegistrationResponseDto;
import notes.project.oaut2registration.service.api.ServiceClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Api(value = "Контроллер для управления клиентами")
public class ServiceClientController {
    private final ServiceClientService serviceClientService;

    @PostMapping()
    @ApiOperation(value = "Регистрация нового клиента")
    public ServiceClientRegistrationResponseDto registerServiceClient(@RequestBody @Valid ServiceClientRegistrationRequestDto request) {
        return serviceClientService.registerServiceClient(request);
    }
}
