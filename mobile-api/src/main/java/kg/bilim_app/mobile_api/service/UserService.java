package kg.bilim_app.mobile_api.service;

import kg.bilim_app.mobile_api.dto.RegisterUserRequest;
import kg.bilim_app.ort.entities.AppUser;
import kg.bilim_app.ort.entities.location.School;
import kg.bilim_app.ort.repositories.AppUserRepository;
import kg.bilim_app.ort.repositories.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public AppUser registerUser(RegisterUserRequest request) {
        if (userRepository.findByTelegramId(request.getTelegramId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));
        AppUser user = new AppUser();
        user.setTelegramId(request.getTelegramId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setLanguage(request.getLanguage());
        user.setSchool(school);
        return userRepository.save(user);
    }

    public AppUser getUser(Long telegramId) {
        return userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public boolean isRegistered(Long telegramId) {
        return userRepository.findByTelegramId(telegramId).isPresent();
    }
}
