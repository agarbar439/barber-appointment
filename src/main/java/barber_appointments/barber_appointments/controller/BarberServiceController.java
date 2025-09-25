package barber_appointments.barber_appointments.controller;

import barber_appointments.barber_appointments.dto.BarberServiceDTO;
import barber_appointments.barber_appointments.service.BarberServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BarberServiceController {

    private final BarberServiceService barberServiceService;

    // Get all the services (Public)
    @GetMapping("/services")
    public ResponseEntity<List<BarberServiceDTO>> getAllServices() {
        return ResponseEntity.ok(barberServiceService.listAllBarberServices());
    }

    // Delete a service by ID (Admin only)
    @DeleteMapping("/admin/services/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        barberServiceService.removeBarberService(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Add a new service (Admin only)
    @PostMapping("/admin/services")
    public ResponseEntity<String> addService(@RequestBody BarberServiceDTO serviceDTO) {
        barberServiceService.addBarberService(serviceDTO.getName(), serviceDTO.getDescription(), serviceDTO.getDurationMinutes(), serviceDTO.getPrice());
        return ResponseEntity.ok("Service added successfully!");
    }

    // Update a service by ID (Admin only)
    @PutMapping("/admin/services/{id}")
    public ResponseEntity<String> updateService(@PathVariable Long id, @RequestBody BarberServiceDTO serviceDTO) {
        barberServiceService.updateBarberService(id, serviceDTO.getName(), serviceDTO.getDescription(), serviceDTO.getDurationMinutes(), serviceDTO.getPrice());
        return ResponseEntity.ok("Service updated successfully!");
    }
}
