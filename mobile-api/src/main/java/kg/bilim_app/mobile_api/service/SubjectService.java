package kg.bilim_app.mobile_api.service;

import kg.bilim_app.common.enums.Language;
import kg.bilim_app.mobile_api.response.SubjectResponse;

import java.util.List;

public interface SubjectService {
    List<SubjectResponse> getSubjects(Language language);
}
