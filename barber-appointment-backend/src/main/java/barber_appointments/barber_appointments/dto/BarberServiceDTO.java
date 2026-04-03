package barber_appointments.barber_appointments.dto;

import lombok.Data;

// DTO for services (used in requests and responses)
@Data
public class BarberServiceDTO {

    private Long id;
    private String name;
    private String description;
    private int durationMinutes;
    private double price;
}
