package kg.bilim_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long telegramId;
    private String firstName;
    private String lastName;
}
