package kg.bilim_app.mobile_api.dto;

import kg.bilim_app.common.enums.Language;
import lombok.Data;

@Data
public class RegisterUserRequest {
    private Long telegramId;
    private String firstName;
    private String lastName;
    private Long schoolId;
    private Language language;
}
