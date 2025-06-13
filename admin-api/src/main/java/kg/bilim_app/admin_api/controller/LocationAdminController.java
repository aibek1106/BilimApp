package kg.bilim_app.admin_api.controller;

import kg.bilim_app.admin_api.request.NewCityRequest;
import kg.bilim_app.admin_api.request.NewRegionRequest;
import kg.bilim_app.admin_api.request.NewSchoolRequest;
import jakarta.validation.Valid;
import kg.bilim_app.admin_api.response.CityResponse;
import kg.bilim_app.admin_api.response.RegionResponse;
import kg.bilim_app.admin_api.response.SchoolResponse;
import kg.bilim_app.admin_api.service.location.LocationAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/location")
@RequiredArgsConstructor
public class LocationAdminController {

    private final LocationAdminService service;

    @PostMapping("/regions")
    public RegionResponse createRegion(@Valid @RequestBody NewRegionRequest request) {
        return service.createRegion(request);
    }

    @PostMapping("/cities")
    public CityResponse createCity(@Valid @RequestBody NewCityRequest request) {
        return service.createCity(request);
    }

    @PostMapping("/schools")
    public SchoolResponse createSchool(@Valid @RequestBody NewSchoolRequest request) {
        return service.createSchool(request);
    }

    @PostMapping("/cache/evict")
    public void evictCache() {
        service.evictCache();
    }
}
