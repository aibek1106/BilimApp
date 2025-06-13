package kg.bilim_app.admin_api.service;

import kg.bilim_app.admin_api.request.NewQuestionRequest;
import kg.bilim_app.admin_api.response.QuestionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface QuestionAdminService {
    QuestionResponse createQuestion(NewQuestionRequest request);

    void uploadQuestions(MultipartFile file) throws IOException;
}
