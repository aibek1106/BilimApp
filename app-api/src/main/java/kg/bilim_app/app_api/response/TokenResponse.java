package kg.bilim_app.app_api.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JWT token response.
 */
public record TokenResponse(@Schema(description = "JWT token") String token) {
}
