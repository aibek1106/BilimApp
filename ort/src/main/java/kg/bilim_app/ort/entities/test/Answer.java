package kg.bilim_app.ort.entities.test;

import jakarta.persistence.*;
import kg.bilim_app.common.models.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Answer extends BaseEntity {

    @Nationalized
    @Column(columnDefinition = "text")
    private String text;

    private Boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Question question;
}