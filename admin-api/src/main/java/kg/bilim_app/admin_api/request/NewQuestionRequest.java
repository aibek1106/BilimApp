package kg.bilim_app.admin_api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * Request to create a new question.
 *
 * @param text       question text
 * @param subgroupId identifier of the subgroup
 * @param answers    possible answers
 */
public record NewQuestionRequest(
        @Schema(description = "question text") @NotBlank String text,
        @Schema(description = "identifier of the subgroup") @NotNull Long subgroupId,
        @Schema(description = "possible answers") @Size(min = 1) List<String> answers) {
}
