package barber_appointments.barber_appointments.model;


import barber_appointments.barber_appointments.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {

    // Primary key auto-incremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Customer name, not null, max length 100
    @Column(name="customer_name", nullable=false, length=100)
    private String customerName;

    // Customer email, not null, max length 150
    @Column(name="customer_email", nullable=false, length=150)
    private String customerEmail;

    // Service relationship, not null
    @ManyToOne(fetch = FetchType.LAZY) // relación N citas → 1 servicio
    @JoinColumn(name = "service_id", nullable = false)
    private BarberService service;

    // Appointment date and time, not null
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    // Status of the appointment, default PENDING
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    // Confirmation token, unique
    @Column(unique = true)
    private String confirmationToken;

    // Timestamps for creation and last update
    @Column(name="created_at", updatable = false, insertable = false)
    private java.sql.Timestamp createdAt;

    @Column(name="updated_at", updatable = false, insertable = false)
    private java.sql.Timestamp updatedAt;
}