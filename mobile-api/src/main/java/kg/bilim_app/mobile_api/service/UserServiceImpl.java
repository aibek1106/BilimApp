package kg.bilim_app.mobile_api.service;

import kg.bilim_app.mobile_api.request.RegisterUserRequest;
import kg.bilim_app.mobile_api.response.UserResponse;
import kg.bilim_app.mobile_api.security.AuthenticatedUserProvider;
import kg.bilim_app.ort.entities.AppUser;
import kg.bilim_app.ort.entities.location.School;
import kg.bilim_app.ort.repositories.AppUserRepository;
import kg.bilim_app.ort.repositories.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final AuthenticatedUserProvider userProvider;

    @Override
    public UserResponse registerUser(RegisterUserRequest request) {
        if (!Objects.equals(request.telegramId(), userProvider.getAuthenticatedUserTelegramId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Telegram id mismatch");
        }

        if (userRepository.findByTelegramId(request.telegramId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        School school = schoolRepository.findById(request.schoolId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));
        AppUser user = new AppUser();
        user.setTelegramId(request.telegramId());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setLanguage(request.language());
        user.setSchool(school);
        user.setUsername(request.username());
        AppUser saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Override
    public UserResponse getUser(Long telegramId) {
        AppUser user = userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return toResponse(user);
    }

    @Override
    public boolean isRegistered(Long telegramId) {
        return userRepository.findByTelegramId(telegramId).isPresent();
    }

    private UserResponse toResponse(AppUser user) {
        return new UserResponse(
                user.getId(),
                user.getTelegramId(),
                user.getFirstName(),
                user.getLastName(),
                user.getLanguage(),
                user.getSchool().getId()
        );
    }
}
