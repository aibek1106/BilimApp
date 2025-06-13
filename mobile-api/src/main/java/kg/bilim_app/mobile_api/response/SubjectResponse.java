package kg.bilim_app.mobile_api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.bilim_app.common.enums.SubjectType;

import java.util.List;

/**
 * Subject information returned by the API.
 *
 * @param id        identifier
 * @param name      subject name
 * @param type      subject type
 * @param subgroups list of subgroups
 */
public record SubjectResponse(
        @Schema(description = "identifier") Long id,
        @Schema(description = "subject name") String name,
        @Schema(description = "subject type") SubjectType type,
        @Schema(description = "list of subgroups") List<SubjectSubgroupResponse> subgroups) {
}
