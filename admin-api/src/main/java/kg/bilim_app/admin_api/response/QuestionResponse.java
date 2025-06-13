package kg.bilim_app.admin_api.response;

import java.util.List;

/**
 * Question data returned by the API.
 *
 * @param id         identifier
 * @param text       question text
 * @param subgroupId identifier of the subgroup
 * @param answers    answer texts
 */
public record QuestionResponse(Long id,
                               String text,
                               Long subgroupId,
                               List<String> answers) {
}
