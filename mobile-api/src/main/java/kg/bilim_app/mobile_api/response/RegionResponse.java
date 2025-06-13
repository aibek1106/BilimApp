package kg.bilim_app.mobile_api.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Region data returned by the API.
 *
 * @param id      identifier
 * @param nameRu  name in Russian
 * @param nameKy  name in Kyrgyz
 */
public record RegionResponse(
        @Schema(description = "identifier") Long id,
        @Schema(description = "name in Russian") String nameRu,
        @Schema(description = "name in Kyrgyz") String nameKy) {
}
