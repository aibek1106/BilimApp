package kg.bilim_app.mobile_api.response;

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
public record UserResponse(Long id,
                           Long telegramId,
                           String firstName,
                           String lastName,
                           Language language,
                           Long schoolId) {
}
