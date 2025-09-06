package barber_appointments.barber_appointments.service;

import barber_appointments.barber_appointments.dto.AppointmentAdminResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentClientResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentRequestDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Service class for managing appointments
public interface AppointmentService {

    // Client creates a new appointment
    AppointmentClientResponseDTO createAppointment(AppointmentRequestDTO request);

    // Delete an appointment by ID (Admin only)
    void deleteAppointment(Long id);

    // Get appointment by token (for both Admin and Client)
    Optional<AppointmentClientResponseDTO> getAppointmentByToken(String token);

    // Obtain all appointments (Admin only)
    List<AppointmentAdminResponseDTO> getAllAppointmentsForAdmin(String status, String date);

    // Get an appointment by ID (Admin only)
     Optional<AppointmentAdminResponseDTO> getAppointmentById(Long id);

     // Change appointment status (Admin only)
     void changeAppointmentStatus(Long id, String status);

    // Confirm appointment by token
    void confirmAppointment(String token);

    // Cancel appointment by token
    void cancelAppointment(String token);

    // Reschedule appointment by token
    void rescheduleAppointment(String token, AppointmentRequestDTO request);
}


