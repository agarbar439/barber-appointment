package barber_appointments.barber_appointments.service.impl;

import barber_appointments.barber_appointments.dto.BarberServiceDTO;
import barber_appointments.barber_appointments.exceptions.ServiceNotFoundException;
import barber_appointments.barber_appointments.model.BarberService;
import barber_appointments.barber_appointments.repository.BarberServiceRepository;
import barber_appointments.barber_appointments.service.BarberServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BarberServiceImpl implements BarberServiceService {

    // Inject the BarberServiceRepository
    private final BarberServiceRepository barberServiceRepository;

    // Add a new barber service
    @Override
    public void addBarberService(String name, String description, int durationMinutes, double price) {
        BarberService service = new BarberService();
        service.setName(name);
        service.setDescription(description);
        service.setDurationMinutes(durationMinutes);
        service.setPrice(price);
        barberServiceRepository.save(service);
    }

    // Remove a barber service by its ID
    @Override
    public void removeBarberService(Long id) {
        // Check if the service exists
        BarberService service = barberServiceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service with id " + id + " not found"));
        // Delete the service
        barberServiceRepository.delete(service);
    }

    // Update a barber service by its ID
    @Override
    public void updateBarberService(Long id, String name, String description, int durationMinutes, double price) {
        BarberService service = barberServiceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service with id " + id + " not found"));
        service.setName(name);
        service.setDescription(description);
        service.setDurationMinutes(durationMinutes);
        service.setPrice(price);
        barberServiceRepository.save(service);
    }

    // List all available barber services
    @Override
    public List<BarberServiceDTO> listAllBarberServices() {
        List<BarberService> services = barberServiceRepository.findAll();
            return services.stream().map(service -> {
                BarberServiceDTO dto = new BarberServiceDTO();
                dto.setId(service.getId());
                dto.setName(service.getName());
                dto.setDescription(service.getDescription());
                dto.setDurationMinutes(service.getDurationMinutes());
                dto.setPrice(service.getPrice());
                return dto;
            }).toList();
    }
}
