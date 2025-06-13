package kg.bilim_app.common.api;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Error information returned by the API.
 */
@Schema(description = "Error information returned by the API")
public record ErrorResponse(@Schema(description = "Error message") String message) {
}
