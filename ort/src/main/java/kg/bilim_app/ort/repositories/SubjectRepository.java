package kg.bilim_app.ort.repositories;

import kg.bilim_app.common.enums.Language;
import kg.bilim_app.ort.entities.test.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByLanguage(Language language);
}
