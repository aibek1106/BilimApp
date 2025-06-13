package kg.bilim_app.ort.repositories;

import kg.bilim_app.ort.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}