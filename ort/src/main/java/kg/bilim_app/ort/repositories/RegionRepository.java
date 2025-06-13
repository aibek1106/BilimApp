package kg.bilim_app.ort.repositories;

import kg.bilim_app.ort.entities.location.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
