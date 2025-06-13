package kg.bilim_app.admin_api.request;
import java.util.List;

/**
 * Request to create a new question.
 *
 * @param text       question text
 * @param subgroupId identifier of the subgroup
 * @param answers    possible answers
 */
public record NewQuestionRequest(String text,
                                 Long subgroupId,
                                 List<String> answers) {
}
