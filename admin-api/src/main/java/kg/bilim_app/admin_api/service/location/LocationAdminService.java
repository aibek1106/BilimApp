package kg.bilim_app.admin_api.service.location;

import kg.bilim_app.admin_api.dto.NewCityRequest;
import kg.bilim_app.admin_api.dto.NewRegionRequest;
import kg.bilim_app.admin_api.dto.NewSchoolRequest;
import kg.bilim_app.ort.entities.location.City;
import kg.bilim_app.ort.entities.location.Region;
import kg.bilim_app.ort.entities.location.School;
import kg.bilim_app.ort.repositories.CityRepository;
import kg.bilim_app.ort.repositories.RegionRepository;
import kg.bilim_app.ort.repositories.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LocationAdminService {

    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final SchoolRepository schoolRepository;

    @CacheEvict(value = {"regions", "cities", "schools"}, allEntries = true)
    public Region createRegion(NewRegionRequest request) {
        Region region = new Region();
        region.setNameRu(request.getNameRu());
        region.setNameKy(request.getNameKy());
        return regionRepository.save(region);
    }

    @CacheEvict(value = {"regions", "cities", "schools"}, allEntries = true)
    public City createCity(NewCityRequest request) {
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found"));
        City city = new City();
        city.setNameRu(request.getNameRu());
        city.setNameKy(request.getNameKy());
        city.setRegion(region);
        return cityRepository.save(city);
    }

    @CacheEvict(value = {"regions", "cities", "schools"}, allEntries = true)
    public School createSchool(NewSchoolRequest request) {
        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        School school = new School();
        school.setNameRu(request.getNameRu());
        school.setNameKy(request.getNameKy());
        school.setCity(city);
        return schoolRepository.save(school);
    }

    @CacheEvict(value = {"regions", "cities", "schools"}, allEntries = true)
    public void evictCache() {
        // cache eviction handled by annotation
    }
}
