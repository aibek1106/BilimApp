package kg.bilim_app.admin_api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
/**
 * Request to create a new school.
 *
 * @param nameRu name in Russian
 * @param nameKy name in Kyrgyz
 * @param cityId identifier of the city
 */
public record NewSchoolRequest(
        @Schema(description = "name in Russian") @NotBlank String nameRu,
        @Schema(description = "name in Kyrgyz") @NotBlank String nameKy,
        @Schema(description = "identifier of the city") @NotNull Long cityId) {
}
