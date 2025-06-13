package kg.bilim_app.admin_api.service.location;

import kg.bilim_app.admin_api.request.NewCityRequest;
import kg.bilim_app.admin_api.request.NewRegionRequest;
import kg.bilim_app.admin_api.request.NewSchoolRequest;
import kg.bilim_app.admin_api.response.CityResponse;
import kg.bilim_app.admin_api.response.RegionResponse;
import kg.bilim_app.admin_api.response.SchoolResponse;
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
public class LocationAdminServiceImpl implements LocationAdminService {

    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final SchoolRepository schoolRepository;

    @Override
    @CacheEvict(value = {"regions", "cities", "schools"}, allEntries = true)
    public RegionResponse createRegion(NewRegionRequest request) {
        Region region = new Region();
        region.setNameRu(request.nameRu());
        region.setNameKy(request.nameKy());
        Region saved = regionRepository.save(region);
        return new RegionResponse(saved.getId(), saved.getNameRu(), saved.getNameKy());
    }

    @Override
    @CacheEvict(value = {"regions", "cities", "schools"}, allEntries = true)
    public CityResponse createCity(NewCityRequest request) {
        Region region = regionRepository.findById(request.regionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found"));
        City city = new City();
        city.setNameRu(request.nameRu());
        city.setNameKy(request.nameKy());
        city.setRegion(region);
        City saved = cityRepository.save(city);
        return new CityResponse(saved.getId(), saved.getNameRu(), saved.getNameKy(), region.getId());
    }

    @Override
    @CacheEvict(value = {"regions", "cities", "schools"}, allEntries = true)
    public SchoolResponse createSchool(NewSchoolRequest request) {
        City city = cityRepository.findById(request.cityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        School school = new School();
        school.setNameRu(request.nameRu());
        school.setNameKy(request.nameKy());
        school.setCity(city);
        School saved = schoolRepository.save(school);
        return new SchoolResponse(saved.getId(), saved.getNameRu(), saved.getNameKy(), city.getId());
    }

    @Override
    @CacheEvict(value = {"regions", "cities", "schools"}, allEntries = true)
    public void evictCache() {
        // cache eviction handled by annotation
    }
}
