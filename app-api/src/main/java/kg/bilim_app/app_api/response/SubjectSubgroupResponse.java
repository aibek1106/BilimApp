package kg.bilim_app.app_api.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Subgroup information returned by the API.
 *
 * @param id   identifier
 * @param name subgroup name
 */
public record SubjectSubgroupResponse(
        @Schema(description = "identifier") Long id,
        @Schema(description = "subgroup name") String name) {
}
