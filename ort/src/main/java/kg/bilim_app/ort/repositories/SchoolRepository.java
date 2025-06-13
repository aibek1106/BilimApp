package kg.bilim_app.ort.repositories;

import kg.bilim_app.ort.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {
    List<School> findByCityId(Long cityId);
}
