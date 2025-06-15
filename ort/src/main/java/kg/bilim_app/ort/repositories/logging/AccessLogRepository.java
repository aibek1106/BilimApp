package kg.bilim_app.ort.repositories.logging;

import kg.bilim_app.ort.entities.logging.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
}
