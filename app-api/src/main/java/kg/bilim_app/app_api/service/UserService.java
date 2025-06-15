package kg.bilim_app.app_api.service;

import kg.bilim_app.app_api.request.RegisterUserRequest;
import kg.bilim_app.app_api.response.UserResponse;

public interface UserService {
    UserResponse registerUser(RegisterUserRequest request);

    UserResponse getUser(Long telegramId);

    boolean isRegistered(Long telegramId);
}
