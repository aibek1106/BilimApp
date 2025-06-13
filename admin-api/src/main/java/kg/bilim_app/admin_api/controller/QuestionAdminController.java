package kg.bilim_app.admin_api.controller;

import kg.bilim_app.admin_api.request.NewQuestionRequest;
import kg.bilim_app.admin_api.response.QuestionResponse;
import kg.bilim_app.admin_api.service.QuestionAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/questions")
@RequiredArgsConstructor
public class QuestionAdminController {

    private final QuestionAdminService service;

    @PostMapping
    public QuestionResponse create(@RequestBody NewQuestionRequest request) {
        return service.createQuestion(request);
    }

    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        service.uploadQuestions(file);
    }
}
