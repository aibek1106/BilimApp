package kg.bilim_app.app_api.service;

import kg.bilim_app.app_api.response.CityResponse;
import kg.bilim_app.app_api.response.RegionResponse;
import kg.bilim_app.app_api.response.SchoolResponse;

import java.util.List;

public interface LocationService {
    List<RegionResponse> getRegions();

    List<CityResponse> getCities(Long regionId);

    List<SchoolResponse> getSchools(Long cityId);
}
