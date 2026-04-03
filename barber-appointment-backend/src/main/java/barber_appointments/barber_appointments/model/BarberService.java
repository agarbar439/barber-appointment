package barber_appointments.barber_appointments.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BarberService {

    // Primary key auto-incremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Service name, not null, max length 100
    @Column(nullable = false, length = 100)
    private String name;

    // Service description, max length 255
    @Column(length = 255)
    private String description;

    // Duration in minutes, not null
    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    // Price, not null
    @Column(nullable = false)
    private double price;
}