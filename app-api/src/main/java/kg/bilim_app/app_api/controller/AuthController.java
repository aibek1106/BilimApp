package kg.bilim_app.app_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import kg.bilim_app.app_api.response.TokenResponse;
import kg.bilim_app.app_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Authorize telegram user")
    @PostMapping
    public TokenResponse authorize(@RequestParam("initData") String initData) {
        String token = authService.authenticate(initData);
        return new TokenResponse(token);
    }
}
