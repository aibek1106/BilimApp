package kg.bilim_app.mobile_api.service;

import kg.bilim_app.ort.entities.location.City;
import kg.bilim_app.ort.entities.location.Region;
import kg.bilim_app.ort.entities.location.School;
import kg.bilim_app.ort.repositories.CityRepository;
import kg.bilim_app.ort.repositories.RegionRepository;
import kg.bilim_app.ort.repositories.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final SchoolRepository schoolRepository;

    @Cacheable("regions")
    public List<Region> getRegions() {
        return regionRepository.findAll();
    }

    @Cacheable(value = "cities", key = "#regionId")
    public List<City> getCities(Long regionId) {
        return cityRepository.findByRegionId(regionId);
    }

    @Cacheable(value = "schools", key = "#cityId")
    public List<School> getSchools(Long cityId) {
        return schoolRepository.findByCityId(cityId);
    }
}
