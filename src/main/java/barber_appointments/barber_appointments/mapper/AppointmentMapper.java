package barber_appointments.barber_appointments.mapper;

import barber_appointments.barber_appointments.dto.AppointmentAdminResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentClientResponseDTO;
import barber_appointments.barber_appointments.model.Appointment;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

// Mapper class to convert between Appointment entity and various DTOs
@Component
public class AppointmentMapper {

    // Formatters for date and time
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    // For the client, email o confirmation
    public AppointmentClientResponseDTO toClientResponseDTO(Appointment appointment) {
        AppointmentClientResponseDTO dto = new AppointmentClientResponseDTO();
        dto.setCustomerName(appointment.getCustomerName());
        dto.setCustomerEmail(appointment.getCustomerEmail());
        dto.setServiceName(appointment.getService().getName());
        dto.setDate(appointment.getDate().toString());
        dto.setTime(appointment.getTime().toString());
        dto.setStatus(appointment.getStatus().name());
        return dto;
    }

    // For the admin panel, all details
    public AppointmentAdminResponseDTO toAdminResponseDTO(Appointment appointment) {
        AppointmentAdminResponseDTO dto = new AppointmentAdminResponseDTO();
        dto.setId(appointment.getId());
        dto.setCustomerName(appointment.getCustomerName());
        dto.setCustomerEmail(appointment.getCustomerEmail());
        dto.setServiceName(appointment.getService().getName());
        dto.setServicePrice(appointment.getService().getPrice());
        dto.setServiceDurationMinutes(appointment.getService().getDurationMinutes());
        dto.setDate(appointment.getDate().format(DATE_FORMAT));
        dto.setTime(appointment.getTime().format(TIME_FORMAT));
        dto.setStatus(appointment.getStatus().name());
        dto.setCreatedAt(appointment.getCreatedAt().toString());
        dto.setUpdatedAt(appointment.getUpdatedAt().toString());
        return dto;
    }
}
