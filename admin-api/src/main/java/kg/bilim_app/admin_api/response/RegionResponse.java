package kg.bilim_app.admin_api.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Region data returned by the API.
 *
 * @param id     identifier
 * @param nameRu Russian name
 * @param nameKy Kyrgyz name
 */
public record RegionResponse(
        @Schema(description = "identifier") Long id,
        @Schema(description = "Russian name") String nameRu,
        @Schema(description = "Kyrgyz name") String nameKy) {
}
