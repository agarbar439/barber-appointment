package barber_appointments.barber_appointments.dto;

import lombok.Data;

// DTO for appointment responses (from server to admin)
// Includes all fields, including customer contact info
@Data
public class AppointmentAdminResponseDTO {
    private Long id;
    private String customerName;
    private String customerEmail;
    private String serviceName;
    private double servicePrice;
    private int serviceDurationMinutes;
    private String date;
    private String time;
    private String status;
    private String createdAt;
    private String updatedAt;
}
