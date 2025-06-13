package kg.bilim_app.ort.repositories;

import kg.bilim_app.ort.entities.location.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByRegionId(Long regionId);
}
