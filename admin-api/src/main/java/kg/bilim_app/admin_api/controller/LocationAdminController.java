package kg.bilim_app.admin_api.controller;

import kg.bilim_app.admin_api.dto.NewCityRequest;
import kg.bilim_app.admin_api.dto.NewRegionRequest;
import kg.bilim_app.admin_api.dto.NewSchoolRequest;
import kg.bilim_app.admin_api.service.location.LocationAdminService;
import kg.bilim_app.ort.entities.location.City;
import kg.bilim_app.ort.entities.location.Region;
import kg.bilim_app.ort.entities.location.School;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/location")
@RequiredArgsConstructor
public class LocationAdminController {

    private final LocationAdminService service;

    @PostMapping("/regions")
    public Region createRegion(@RequestBody NewRegionRequest request) {
        return service.createRegion(request);
    }

    @PostMapping("/cities")
    public City createCity(@RequestBody NewCityRequest request) {
        return service.createCity(request);
    }

    @PostMapping("/schools")
    public School createSchool(@RequestBody NewSchoolRequest request) {
        return service.createSchool(request);
    }

    @PostMapping("/cache/evict")
    public void evictCache() {
        service.evictCache();
    }
}
