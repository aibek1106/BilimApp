package kg.bilim_app.app_api.service;

import kg.bilim_app.app_api.response.CityResponse;
import kg.bilim_app.app_api.response.RegionResponse;
import kg.bilim_app.app_api.response.SchoolResponse;
import kg.bilim_app.ort.repositories.CityRepository;
import kg.bilim_app.ort.repositories.RegionRepository;
import kg.bilim_app.ort.repositories.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final SchoolRepository schoolRepository;

    @Override
    @Cacheable("regions")
    public List<RegionResponse> getRegions() {
        return regionRepository.findAll().stream()
                .map(r -> new RegionResponse(r.getId(), r.getNameRu(), r.getNameKy()))
                .toList();
    }

    @Override
    @Cacheable(value = "cities", key = "#regionId")
    public List<CityResponse> getCities(Long regionId) {
        return cityRepository.findByRegionId(regionId).stream()
                .map(c -> new CityResponse(c.getId(), c.getNameRu(), c.getNameKy(), c.getRegion().getId()))
                .toList();
    }

    @Override
    @Cacheable(value = "schools", key = "#cityId")
    public List<SchoolResponse> getSchools(Long cityId) {
        return schoolRepository.findByCityId(cityId).stream()
                .map(s -> new SchoolResponse(s.getId(), s.getNameRu(), s.getNameKy(), s.getCity().getId()))
                .toList();
    }
}
