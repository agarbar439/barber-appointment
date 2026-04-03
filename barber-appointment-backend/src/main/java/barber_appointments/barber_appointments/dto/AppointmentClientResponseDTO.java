package barber_appointments.barber_appointments.dto;

import lombok.Data;

// DTO for appointment responses (from server to client)
// Only includes fields relevant to the client
@Data
public class AppointmentClientResponseDTO {

    private String customerName;
    private String serviceName;
    private String customerEmail;
    private String date;   // "2025-09-05"
    private String time;   // "16:30"
    private String status; // CONFIRMED / CANCELLED
}
