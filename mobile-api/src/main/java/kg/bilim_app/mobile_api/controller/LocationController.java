package kg.bilim_app.mobile_api.controller;

import kg.bilim_app.mobile_api.service.LocationService;
import kg.bilim_app.ort.entities.City;
import kg.bilim_app.ort.entities.Region;
import kg.bilim_app.ort.entities.School;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService service;

    @GetMapping("/regions")
    public List<Region> getRegions() {
        return service.getRegions();
    }

    @GetMapping("/cities/{regionId}")
    public List<City> getCities(@PathVariable Long regionId) {
        return service.getCities(regionId);
    }

    @GetMapping("/schools/{cityId}")
    public List<School> getSchools(@PathVariable Long cityId) {
        return service.getSchools(cityId);
    }
}
