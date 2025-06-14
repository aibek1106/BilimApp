package kg.bilim_app.ort.entities.test;

import jakarta.persistence.*;
import kg.bilim_app.common.enums.Language;
import kg.bilim_app.common.enums.SubjectType;
import kg.bilim_app.common.models.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Subject extends BaseEntity {

    @Nationalized
    private String name;

    private SubjectType type;

    @Enumerated(EnumType.STRING)
    private Language language;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<SubjectSubgroup> subgroups;
}