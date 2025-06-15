package kg.bilim_app.web_api.service;

import kg.bilim_app.web_api.response.SubjectResponse;

import kg.bilim_app.common.enums.Language;
import java.util.List;

public interface SubjectService {
    List<SubjectResponse> getSubjects(Language language);
}
