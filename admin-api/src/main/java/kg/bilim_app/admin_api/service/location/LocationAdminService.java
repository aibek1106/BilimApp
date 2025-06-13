package kg.bilim_app.admin_api.service.location;

import kg.bilim_app.admin_api.request.NewCityRequest;
import kg.bilim_app.admin_api.request.NewRegionRequest;
import kg.bilim_app.admin_api.request.NewSchoolRequest;
import kg.bilim_app.admin_api.response.CityResponse;
import kg.bilim_app.admin_api.response.RegionResponse;
import kg.bilim_app.admin_api.response.SchoolResponse;

public interface LocationAdminService {
    RegionResponse createRegion(NewRegionRequest request);

    CityResponse createCity(NewCityRequest request);

    SchoolResponse createSchool(NewSchoolRequest request);

    void evictCache();
}
