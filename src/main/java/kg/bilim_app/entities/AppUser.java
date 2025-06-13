package kg.bilim_app.entities;

import jakarta.persistence.*;
import kg.bilim_app.models.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Nationalized;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class AppUser extends BaseEntity {

    private Long telegramId;

    @Nationalized
    private String firstName;

    @Nationalized
    private String lastName;
}