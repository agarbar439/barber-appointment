package barber_appointments.barber_appointments.controller;

import barber_appointments.barber_appointments.dto.AppointmentAdminResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentClientResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentRequestDTO;
import barber_appointments.barber_appointments.service.AppointmentService;
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


    // Client creates a new appointment
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentClientResponseDTO> create(@RequestBody AppointmentRequestDTO request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
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

    // Delete an appointment by ID (Admin only)
    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }


}
