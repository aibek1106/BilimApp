package kg.bilim_app.admin_api.dto;

import lombok.Data;

@Data
public class NewSchoolRequest {
    private String nameRu;
    private String nameKy;
    private Long cityId;
}
