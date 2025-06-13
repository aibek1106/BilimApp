package kg.bilim_app.admin_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewQuestionRequest {
    private String text;
    private Long subgroupId;
    private List<String> answers;
}
