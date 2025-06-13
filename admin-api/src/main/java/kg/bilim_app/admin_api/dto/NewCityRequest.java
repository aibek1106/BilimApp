package kg.bilim_app.admin_api.dto;

import lombok.Data;

@Data
public class NewCityRequest {
    private String nameRu;
    private String nameKy;
    private Long regionId;
}
