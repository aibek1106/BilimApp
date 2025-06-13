package kg.bilim_app.admin_api.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * City data returned by the API.
 *
 * @param id       identifier
 * @param nameRu   Russian name
 * @param nameKy   Kyrgyz name
 * @param regionId identifier of the region
 */
public record CityResponse(
        @Schema(description = "identifier") Long id,
        @Schema(description = "Russian name") String nameRu,
        @Schema(description = "Kyrgyz name") String nameKy,
        @Schema(description = "identifier of the region") Long regionId) {
}
