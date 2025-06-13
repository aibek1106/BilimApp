package kg.bilim_app.ort.entities;

import jakarta.persistence.*;
import kg.bilim_app.common.models.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(indexes = {
        @Index(columnList = "region_id"),
        @Index(columnList = "name_ru"),
        @Index(columnList = "name_ky")
})
public class City extends BaseEntity {

    @Nationalized
    @Column(name = "name_ru")
    private String nameRu;

    @Nationalized
    @Column(name = "name_ky")
    private String nameKy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Region region;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<School> schools;
}
