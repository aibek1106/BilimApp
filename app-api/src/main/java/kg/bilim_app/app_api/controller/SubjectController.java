package kg.bilim_app.app_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import kg.bilim_app.app_api.response.SubjectResponse;
import kg.bilim_app.app_api.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService service;

    @Operation(summary = "Get list of subjects with subgroups")
    @GetMapping
    public List<SubjectResponse> getSubjects() {
        return service.getSubjects();
    }
}
