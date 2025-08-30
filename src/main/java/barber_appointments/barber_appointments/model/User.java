package barber_appointments.barber_appointments.model;

import barber_appointments.barber_appointments.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    // Primary key auto-incremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique username, not null, max length 50
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    // Not null password
    @Column(nullable = false)
    private String password; // guardada en BCrypt

    // User role, default ADMIN
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ADMIN;
}