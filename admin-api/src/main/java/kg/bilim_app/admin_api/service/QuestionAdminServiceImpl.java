package kg.bilim_app.admin_api.service;

import kg.bilim_app.admin_api.request.NewQuestionRequest;
import kg.bilim_app.admin_api.response.QuestionResponse;
import kg.bilim_app.ort.entities.test.Answer;
import kg.bilim_app.ort.entities.test.Question;
import kg.bilim_app.ort.entities.test.SubjectSubgroup;
import kg.bilim_app.ort.repositories.QuestionRepository;
import kg.bilim_app.ort.repositories.SubjectSubgroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionAdminServiceImpl implements QuestionAdminService {

    private final QuestionRepository questionRepository;
    private final SubjectSubgroupRepository subgroupRepository;

    @Override
    public QuestionResponse createQuestion(NewQuestionRequest request) {
        SubjectSubgroup subgroup = subgroupRepository.findById(request.subgroupId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subgroup not found"));
        Question question = new Question();
        question.setText(request.text());
        question.setSubgroup(subgroup);
        List<Answer> answers = new ArrayList<>();
        if (request.answers() != null) {
            for (String a : request.answers()) {
                Answer ans = new Answer();
                ans.setText(a);
                ans.setQuestion(question);
                answers.add(ans);
            }
        }
        question.setAnswers(answers);
        Question saved = questionRepository.save(question);
        return new QuestionResponse(
                saved.getId(),
                saved.getText(),
                saved.getSubgroup().getId(),
                saved.getAnswers().stream().map(Answer::getText).toList()
        );
    }

    @Override
    public void uploadQuestions(MultipartFile file) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    continue;
                }
                List<String> ans = new ArrayList<>();
                for (int i = 2; i < parts.length; i++) {
                    ans.add(parts[i].trim());
                }
                NewQuestionRequest rq = new NewQuestionRequest(
                        parts[1].trim(),
                        Long.parseLong(parts[0].trim()),
                        ans
                );
                createQuestion(rq);
            }
        }
    }
}
