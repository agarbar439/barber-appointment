package barber_appointments.barber_appointments.repository;

import barber_appointments.barber_appointments.model.Appointment;
import barber_appointments.barber_appointments.model.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

// This interface extends JpaRepository to provide CRUD operations for Appointment entities
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Search all appointments by their status (e.g., PENDING, CONFIRMED, CANCELED)
    List<Appointment> findAllByStatus(AppointmentStatus status);

    // Search all appointments by a specific date
    List<Appointment> findAllByDate(LocalDate date);

    // Search for an appointment by its unique confirmation token
    Optional<Appointment> findByConfirmationToken(String token);

    @Query("""
    SELECT a FROM Appointment a 
    JOIN a.service s
    WHERE a.date = :date 
      AND a.time < :endTime
      AND FUNCTION('ADDTIME', a.time, FUNCTION('SEC_TO_TIME', (s.durationMinutes * 60))) > :startTime
""")
    List<Appointment> findOverlappingBookings(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);

}
