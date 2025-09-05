package barber_appointments.barber_appointments.controller;

import barber_appointments.barber_appointments.dto.AppointmentClientResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentRequestDTO;
import barber_appointments.barber_appointments.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppointmentController {

    private  final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentClientResponseDTO> create(@RequestBody AppointmentRequestDTO request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }

}
