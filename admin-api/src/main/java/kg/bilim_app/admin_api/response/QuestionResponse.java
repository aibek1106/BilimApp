package kg.bilim_app.admin_api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * Question data returned by the API.
 *
 * @param id         identifier
 * @param text       question text
 * @param subgroupId identifier of the subgroup
 * @param answers    answer texts
 */
public record QuestionResponse(
        @Schema(description = "identifier") Long id,
        @Schema(description = "question text") String text,
        @Schema(description = "identifier of the subgroup") Long subgroupId,
        @Schema(description = "answer texts") List<String> answers) {
}
