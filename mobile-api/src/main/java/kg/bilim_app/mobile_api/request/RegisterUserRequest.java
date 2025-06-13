package kg.bilim_app.mobile_api.request;
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
public record RegisterUserRequest(Long telegramId,
                                  String firstName,
                                  String lastName,
                                  Long schoolId,
                                  Language language) {
}
