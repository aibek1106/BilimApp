package kg.bilim_app.admin_api.controller;

import kg.bilim_app.admin_api.request.NewCityRequest;
import kg.bilim_app.admin_api.request.NewRegionRequest;
import kg.bilim_app.admin_api.request.NewSchoolRequest;
import kg.bilim_app.admin_api.response.CityResponse;
import kg.bilim_app.admin_api.response.RegionResponse;
import kg.bilim_app.admin_api.response.SchoolResponse;
import kg.bilim_app.admin_api.service.location.LocationAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/location")
@RequiredArgsConstructor
public class LocationAdminController {

    private final LocationAdminService service;

    @PostMapping("/regions")
    public RegionResponse createRegion(@RequestBody NewRegionRequest request) {
        return service.createRegion(request);
    }

    @PostMapping("/cities")
    public CityResponse createCity(@RequestBody NewCityRequest request) {
        return service.createCity(request);
    }

    @PostMapping("/schools")
    public SchoolResponse createSchool(@RequestBody NewSchoolRequest request) {
        return service.createSchool(request);
    }

    @PostMapping("/cache/evict")
    public void evictCache() {
        service.evictCache();
    }
}
