package kg.bilim_app.ort.repositories;

import kg.bilim_app.ort.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}