package kg.bilim_app.mobile_api.controller;

import kg.bilim_app.mobile_api.service.LocationService;
import kg.bilim_app.ort.entities.location.City;
import kg.bilim_app.ort.entities.location.Region;
import kg.bilim_app.ort.entities.location.School;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService service;

    @Operation(summary = "Get list of regions")
    @GetMapping("/regions")
    public List<Region> getRegions() {
        return service.getRegions();
    }

    @Operation(summary = "Get cities by region id")
    @GetMapping("/cities/{regionId}")
    public List<City> getCities(@PathVariable Long regionId) {
        return service.getCities(regionId);
    }

    @Operation(summary = "Get schools by city id")
    @GetMapping("/schools/{cityId}")
    public List<School> getSchools(@PathVariable Long cityId) {
        return service.getSchools(cityId);
    }
}
