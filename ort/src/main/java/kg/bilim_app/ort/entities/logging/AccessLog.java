package kg.bilim_app.ort.entities.logging;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import kg.bilim_app.common.models.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "access_logs")
public class AccessLog extends BaseEntity {

    @Column(name = "ip_address")
    private String ipAddress;

    private String path;

    private String method;
}
