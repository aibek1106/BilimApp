package kg.bilim_app.mobile_api.controller;

import kg.bilim_app.mobile_api.request.RegisterUserRequest;
import kg.bilim_app.mobile_api.response.UserResponse;
import kg.bilim_app.mobile_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/mobile/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterUserRequest request) {
        return service.registerUser(request);
    }

    @Operation(summary = "Get user by telegram id")
    @GetMapping("/{telegramId}")
    public UserResponse getUser(@PathVariable Long telegramId) {
        return service.getUser(telegramId);
    }

    @Operation(summary = "Check if user is registered")
    @GetMapping("/{telegramId}/exists")
    public boolean isRegistered(@PathVariable Long telegramId) {
        return service.isRegistered(telegramId);
    }
}
