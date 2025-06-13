package kg.bilim_app.ort.entities;

import jakarta.persistence.*;
import kg.bilim_app.common.enums.Language;
import kg.bilim_app.common.models.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Nationalized;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class AppUser extends BaseEntity {

    private Long telegramId;

    @Nationalized
    private String firstName;

    @Nationalized
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private School school;
}