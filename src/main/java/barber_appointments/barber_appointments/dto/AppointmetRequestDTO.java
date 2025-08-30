package barber_appointments.barber_appointments.dto;

import lombok.Data;

// DTO for appointment requests (from client to server)
@Data
public class AppointmetRequestDTO {
    private String customerName;
    private String customerEmail;
    private Long serviceId; // ID of the service being requested
    private String date; // Format "YYYY-MM-DD"
    private String time; // Format "HH:MM:SS"
}
