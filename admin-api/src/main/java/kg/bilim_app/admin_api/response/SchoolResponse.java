package kg.bilim_app.admin_api.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * School data returned by the API.
 *
 * @param id     identifier
 * @param nameRu Russian name
 * @param nameKy Kyrgyz name
 * @param cityId identifier of the city
 */
public record SchoolResponse(
        @Schema(description = "identifier") Long id,
        @Schema(description = "Russian name") String nameRu,
        @Schema(description = "Kyrgyz name") String nameKy,
        @Schema(description = "identifier of the city") Long cityId) {
}
