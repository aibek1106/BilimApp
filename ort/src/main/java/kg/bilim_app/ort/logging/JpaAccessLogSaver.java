package kg.bilim_app.ort.logging;

import kg.bilim_app.common.logging.AccessLogSaver;
import kg.bilim_app.ort.entities.logging.AccessLog;
import kg.bilim_app.ort.repositories.logging.AccessLogRepository;
import org.springframework.stereotype.Component;

@Component
public class JpaAccessLogSaver implements AccessLogSaver {

    private final AccessLogRepository repository;

    public JpaAccessLogSaver(AccessLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(String ip, String path, String method) {
        AccessLog log = new AccessLog();
        log.setIpAddress(ip);
        log.setPath(path);
        log.setMethod(method);
        repository.save(log);
    }
}
