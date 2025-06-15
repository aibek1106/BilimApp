package kg.bilim_app.app_api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.bilim_app.common.enums.Language;

/**
 * Representation of a user returned by the API.
 *
 * @param id         identifier
 * @param telegramId telegram identifier
 * @param firstName  first name
 * @param lastName   last name
 * @param language   preferred language
 * @param schoolId   identifier of the user's school
 */
public record UserResponse(
        @Schema(description = "identifier") Long id,
        @Schema(description = "telegram identifier") Long telegramId,
        @Schema(description = "first name") String firstName,
        @Schema(description = "last name") String lastName,
        @Schema(description = "preferred language") Language language,
        @Schema(description = "identifier of the user's school") Long schoolId) {
}
