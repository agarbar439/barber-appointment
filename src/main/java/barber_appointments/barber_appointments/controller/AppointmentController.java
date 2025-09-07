package barber_appointments.barber_appointments.controller;

import barber_appointments.barber_appointments.dto.AppointmentAdminResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentClientResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentRequestDTO;
import barber_appointments.barber_appointments.dto.BarberServiceDTO;
import barber_appointments.barber_appointments.service.AppointmentService;
import barber_appointments.barber_appointments.service.BarberServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppointmentController {

    private  final AppointmentService appointmentService;
    private final BarberServiceService barberServiceService;

    // Client role

    // Client creates a new appointment
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentClientResponseDTO> create(@RequestBody AppointmentRequestDTO request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }

    // Confirm appointment by token
    @GetMapping("/appointments/confirm")
    public ResponseEntity<String> confirmAppointment(@RequestParam String token) {
        appointmentService.confirmAppointment(token);
        return ResponseEntity.ok("Appointment confirmed successfully!");
    }

    // Cancel appointment by token
    @GetMapping("/appointments/cancel")
    public ResponseEntity<String> cancelAppointment(@RequestParam String token) {
        appointmentService.cancelAppointment(token);
        return ResponseEntity.ok("Appointment cancelled successfully!");
    }

    // Reschedule appointment by token
    @PutMapping("/appointments/reschedule")
    public ResponseEntity<String> rescheduleAppointment(@RequestParam String token, @RequestBody AppointmentRequestDTO request) {
        appointmentService.rescheduleAppointment(token, request);
        return ResponseEntity.ok("Appointment rescheduled successfully!");
    }

    // Get all the services
    @GetMapping("/services")
    public ResponseEntity<List<BarberServiceDTO>> getAllServices() {
        return ResponseEntity.ok(barberServiceService.listAllBarberServices());
    }


    // Admin role

    // Obtain all appointments (Admin only) with optional filters for status and date
    @GetMapping("/admin/appointments")
    public ResponseEntity<List<AppointmentAdminResponseDTO>> getAppointmentsForAdmin(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String date
    ) {
        return ResponseEntity.ok(appointmentService.getAllAppointmentsForAdmin(status, date));
    }

    // Get appointment by ID (Admin only)
    @GetMapping("/admin/appointments/{id}")
    public ResponseEntity<AppointmentAdminResponseDTO> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an appointment by ID (Admin only)
    @DeleteMapping("/admin/appointments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Change appointment status (Admin only)
    @PutMapping("/admin/appointments/{id}/status")
    public ResponseEntity<String> changeAppointmentStatus(@PathVariable Long id, @RequestBody String status) {
        appointmentService.changeAppointmentStatus(id, status);
        return ResponseEntity.ok("Appointment status updated successfully!");

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
