package kg.bilim_app.ort.repositories;

import kg.bilim_app.ort.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}