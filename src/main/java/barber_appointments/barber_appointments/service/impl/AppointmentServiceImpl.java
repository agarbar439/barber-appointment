package barber_appointments.barber_appointments.service.impl;

import barber_appointments.barber_appointments.dto.AppointmentAdminResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentClientResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentRequestDTO;
import barber_appointments.barber_appointments.exceptions.OverlappingBookingsException;
import barber_appointments.barber_appointments.exceptions.ServiceNotFoundException;
import barber_appointments.barber_appointments.exceptions.TimeSlotUnavailableException;
import barber_appointments.barber_appointments.exceptions.WorkingHoursException;
import barber_appointments.barber_appointments.mapper.AppointmentMapper;
import barber_appointments.barber_appointments.model.Appointment;
import barber_appointments.barber_appointments.model.BarberService;
import barber_appointments.barber_appointments.model.enums.AppointmentStatus;
import barber_appointments.barber_appointments.repository.AppointmentRepository;
import barber_appointments.barber_appointments.repository.BarberServiceRepository;
import barber_appointments.barber_appointments.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {

    // Repository for appointment data access
    private final AppointmentRepository appointmentRepository;
    private final BarberServiceRepository barberServiceRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentClientResponseDTO createAppointment(AppointmentRequestDTO request) {
        // Check for availability, save appointment, send confirmation email, etc.
        // Check the availability of the requested time slot
        // If available, save the appointment to the database
        // Generate a unique confirmation token
        // Send a confirmation email to the client with the token
        // Return the appointment details along with the confirmation token

        // Calculate total duration of the service
        int totalDuration = barberServiceRepository.findById(request.getServiceId())
                .orElseThrow(() ->  new ServiceNotFoundException("Service not found"))
                .getDurationMinutes();


        // Calculate end time based on start time and total duration
        LocalDateTime startTime = LocalDateTime.parse(request.getDate() + "T" + request.getTime());
        LocalDateTime endTime = startTime.plusMinutes(totalDuration);

        // Check if the time slot is available
        if(!isTimeSlotAvailable(startTime,endTime)){
            throw new TimeSlotUnavailableException("Requested time slot is not available");
        }

        // Get service id
        BarberService barberService = barberServiceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // If available, create and save the appointment
        Appointment appointment = new Appointment();
        appointment.setCustomerName(request.getCustomerName());
        appointment.setCustomerEmail(request.getCustomerEmail());
        appointment.setService(barberService);
        appointment.setConfirmationToken(UUID.randomUUID().toString());
        appointment.setDate(startTime.toLocalDate());
        appointment.setTime(startTime.toLocalTime());

        // Send the confirmation email
       // emailService.sendConfirmationEmail(savedAppointment);
        /*Appointment savedAppointment = appointmentRepository.save(appointment);
emailService.sendConfirmationEmail(savedAppointment);
return savedAppointment;
*/
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toClientResponseDTO(savedAppointment);
    }

    // Method to check if a time slot is available
    private boolean isTimeSlotAvailable(LocalDateTime bookingStartTime, LocalDateTime
            bookingEndTime)  {
        // Implement logic to check for overlapping appointments in the database

        // Check working hours
        LocalDateTime openingTime = bookingStartTime.withHour(9).withMinute(0).withSecond(0);
        LocalDateTime closingTime = bookingStartTime.withHour(18).withMinute(0).withSecond(0);

        if (bookingStartTime.isBefore(openingTime) || bookingEndTime.isAfter(closingTime)) {
            throw new WorkingHoursException("Requested time is outside of working hours (9 AM - 6 PM)");
        }

        // Check for overlapping appointments in the database
        List<Appointment> overlappingBookings  = appointmentRepository.findOverlappingBookings (
               bookingStartTime.toLocalDate(),
                bookingStartTime.toLocalTime(),
                bookingEndTime.toLocalTime()
        );

        // If there are overlapping bookings, the time slot is not available
        if(!overlappingBookings.isEmpty()) {
            throw new OverlappingBookingsException("Requested time slot is not available");
        }
    return true;
    }

    // Admin only: Delete appointment by ID
    @Override
    public void deleteAppointment(Long id) {
       Appointment appointment = appointmentRepository.findById(id)
               .orElseThrow(() -> new ServiceNotFoundException("Appointment not found"));
       appointmentRepository.delete(appointment);
    }

    @Override
    public Optional<AppointmentClientResponseDTO> getAppointmentByToken(String token) {
        return Optional.empty();
    }

    // Admin only: Get all appointments with optional filtering by status and date
    @Override
    public List<AppointmentAdminResponseDTO> getAllAppointmentsForAdmin(String status, String date) {
        List<Appointment> appointments;

        // Filter by status and/or date if provided
        if (status != null && date != null) {
            // Find by both status and date
            appointments = appointmentRepository.findAllByStatusAndDate(
                    AppointmentStatus.valueOf(status),
                    LocalDate.parse(date)
            );
        } else if (status != null) {
            appointments = appointmentRepository.findAllByStatus(AppointmentStatus.valueOf(status));
        } else if (date != null) {
            appointments = appointmentRepository.findAllByDate(LocalDate.parse(date));
        } else {
            appointments = appointmentRepository.findAll();
        }

        // Map to DTOs and return
        return appointments.stream()
                .map(appointmentMapper::toAdminResponseDTO)
                .toList();
    }

    // Admin only: Get appointment by ID
    @Override
    public Optional<AppointmentAdminResponseDTO> getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(appointmentMapper::toAdminResponseDTO);
    }

    // Admin only: Change appointment status
    @Override
    public void changeAppointmentStatus(Long id, String status) {
        // Get the appointment by ID
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        try {
            AppointmentStatus newStatus = AppointmentStatus.valueOf(status.toUpperCase());
            appointment.setStatus(newStatus);
            appointmentRepository.save(appointment);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }

    // Client: Confirm appointment by token
    @Override
    public void confirmAppointment(String token) {
        Appointment appointment = appointmentRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new ServiceNotFoundException("Appointment not found"));

        // Only pending appointments can be confirmed
        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            throw new IllegalStateException("Only pending appointments can be confirmed");
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);

    }

    // Client: Cancel appointment by token
    @Override
    public void cancelAppointment(String token) {
        Appointment appointment = appointmentRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new ServiceNotFoundException("Appointment not found"));
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Appointment is already canceled");
        } else {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            appointmentRepository.save(appointment);
        }
    }

    // Client: Reschedule appointment by token
    @Override
    public void rescheduleAppointment(String token, AppointmentRequestDTO request) {
        // Implementation for rescheduling an appointment
        Appointment appointment = appointmentRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new ServiceNotFoundException("Appointment not found"));
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot reschedule a canceled appointment");
        } else {
            // Update appointment details based on the request
            appointment.setCustomerName(request.getCustomerName());
            appointment.setCustomerEmail(request.getCustomerEmail());
            BarberService barberService = barberServiceRepository.findById(request.getServiceId())
                    .orElseThrow(() -> new ServiceNotFoundException("Service not found"));
            appointment.setService(barberService);
            appointment.setDate(LocalDate.parse(request.getDate()));
            appointment.setTime(java.time.LocalTime.parse(request.getTime()));
            appointmentRepository.save(appointment);
        }
    }
}
