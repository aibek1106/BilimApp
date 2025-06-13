package kg.bilim_app.admin_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableCaching
@EnableJpaRepositories(basePackages = "kg.bilim_app.ort.repositories")
@EntityScan(basePackages = "kg.bilim_app.ort.entities")
@SpringBootApplication(scanBasePackages = "kg.bilim_app")
public class AdminApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
    }
}
