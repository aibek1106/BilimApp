package kg.bilim_app.mobile_api.controller;

import kg.bilim_app.mobile_api.dto.RegisterUserRequest;
import kg.bilim_app.mobile_api.service.UserService;
import kg.bilim_app.ort.entities.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public AppUser register(@RequestBody RegisterUserRequest request) {
        return service.registerUser(request);
    }

    @Operation(summary = "Get user by telegram id")
    @GetMapping("/{telegramId}")
    public AppUser getUser(@PathVariable Long telegramId) {
        return service.getUser(telegramId);
    }

    @Operation(summary = "Check if user is registered")
    @GetMapping("/{telegramId}/exists")
    public boolean isRegistered(@PathVariable Long telegramId) {
        return service.isRegistered(telegramId);
    }
}
