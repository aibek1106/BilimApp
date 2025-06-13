package kg.bilim_app.ort.entities.location;

import jakarta.persistence.*;
import kg.bilim_app.common.models.BaseEntity;
import kg.bilim_app.ort.entities.AppUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(indexes = {
        @Index(columnList = "city_id"),
        @Index(columnList = "name_ru"),
        @Index(columnList = "name_ky")
})
public class School extends BaseEntity {

    @Nationalized
    @Column(name = "name_ru")
    private String nameRu;

    @Nationalized
    @Column(name = "name_ky")
    private String nameKy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private City city;

    @OneToMany(mappedBy = "school")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AppUser> users;
}
