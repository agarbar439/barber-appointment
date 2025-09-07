package barber_appointments.barber_appointments.service;

import barber_appointments.barber_appointments.dto.AppointmentClientResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentRequestDTO;
import barber_appointments.barber_appointments.dto.BarberServiceDTO;

import java.util.List;

// Service class for managing barber services
public interface BarberServiceService {

    // Method to add a new barber service
    void addBarberService(String name, String description, int durationMinutes, double price);

    // Method to remove a barber service by its ID
    void removeBarberService(Long id);

    // Method to update an existing barber service
    void updateBarberService(Long id, String name, String description, int durationMinutes, double price);

    // Method to list all available barber services
    List<BarberServiceDTO> listAllBarberServices();

}
