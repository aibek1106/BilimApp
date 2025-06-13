package kg.bilim_app.mobile_api.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * City data returned by the API.
 *
 * @param id       identifier
 * @param nameRu   name in Russian
 * @param nameKy   name in Kyrgyz
 * @param regionId identifier of the region
 */
public record CityResponse(
        @Schema(description = "identifier") Long id,
        @Schema(description = "name in Russian") String nameRu,
        @Schema(description = "name in Kyrgyz") String nameKy,
        @Schema(description = "identifier of the region") Long regionId) {
}
