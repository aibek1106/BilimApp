package kg.bilim_app.mobile_api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.bilim_app.common.enums.Language;

/**
 * Request to register a new user.
 *
 * @param telegramId Telegram identifier
 * @param firstName  first name
 * @param lastName   last name
 * @param schoolId   identifier of the school
 * @param language   preferred language
 */
public record RegisterUserRequest(
        @Schema(description = "Telegram identifier") @NotNull Long telegramId,
        @Schema(description = "first name") @NotBlank String firstName,
        @Schema(description = "last name") String lastName,
        @Schema(description = "username") String username,
        @Schema(description = "identifier of the school") @NotNull Long schoolId,
        @Schema(description = "preferred language") @NotNull Language language) {
}
