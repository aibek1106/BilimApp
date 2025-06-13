package kg.bilim_app.admin_api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
/**
 * Request to create a new region.
 *
 * @param nameRu name in Russian
 * @param nameKy name in Kyrgyz
 */
public record NewRegionRequest(
        @Schema(description = "name in Russian") @NotBlank String nameRu,
        @Schema(description = "name in Kyrgyz") @NotBlank String nameKy) {
}
