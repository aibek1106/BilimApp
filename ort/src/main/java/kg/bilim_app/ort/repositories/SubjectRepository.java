package kg.bilim_app.ort.repositories;

import kg.bilim_app.ort.entities.test.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}