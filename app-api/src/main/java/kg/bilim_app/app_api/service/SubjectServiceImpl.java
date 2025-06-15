package kg.bilim_app.app_api.service;

import kg.bilim_app.common.enums.Language;
import kg.bilim_app.app_api.response.SubjectResponse;
import kg.bilim_app.app_api.response.SubjectSubgroupResponse;
import kg.bilim_app.ort.entities.test.Subject;
import kg.bilim_app.ort.repositories.SubjectRepository;
import kg.bilim_app.ort.entities.AppUser;
import kg.bilim_app.app_api.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final AuthenticatedUserProvider userProvider;

    @Override
    public List<SubjectResponse> getSubjects() {
        AppUser user = userProvider.getAuthenticatedUser();
        Language language = user.getLanguage();
        return subjectRepository.findByLanguage(language).stream()
                .map(this::toResponse)
                .toList();
    }

    private SubjectResponse toResponse(Subject subject) {
        List<SubjectSubgroupResponse> subgroups = subject.getSubgroups().stream()
                .map(sg -> new SubjectSubgroupResponse(sg.getId(), sg.getName()))
                .toList();
        return new SubjectResponse(
                subject.getId(),
                subject.getName(),
                subject.getType(),
                subgroups
        );
    }
}
