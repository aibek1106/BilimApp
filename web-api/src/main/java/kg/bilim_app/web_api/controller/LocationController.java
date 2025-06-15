package kg.bilim_app.web_api.controller;

import kg.bilim_app.web_api.response.CityResponse;
import kg.bilim_app.web_api.response.RegionResponse;
import kg.bilim_app.web_api.response.SchoolResponse;
import kg.bilim_app.web_api.service.LocationService;
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
    public List<RegionResponse> getRegions() {
        return service.getRegions();
    }

    @Operation(summary = "Get cities by region id")
    @GetMapping("/cities/{regionId}")
    public List<CityResponse> getCities(@PathVariable Long regionId) {
        return service.getCities(regionId);
    }

    @Operation(summary = "Get schools by city id")
    @GetMapping("/schools/{cityId}")
    public List<SchoolResponse> getSchools(@PathVariable Long cityId) {
        return service.getSchools(cityId);
    }
}
