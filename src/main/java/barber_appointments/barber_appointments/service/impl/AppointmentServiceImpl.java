package barber_appointments.barber_appointments.service.impl;

import barber_appointments.barber_appointments.dto.AppointmentAdminResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentClientResponseDTO;
import barber_appointments.barber_appointments.dto.AppointmentRequestDTO;
import barber_appointments.barber_appointments.dto.BarberServiceDTO;
import barber_appointments.barber_appointments.exceptions.OverlappingBookingsException;
import barber_appointments.barber_appointments.exceptions.ServiceNotFoundException;
import barber_appointments.barber_appointments.exceptions.TimeSlotUnavailableException;
import barber_appointments.barber_appointments.exceptions.WorkingHoursException;
import barber_appointments.barber_appointments.mapper.AppointmentMapper;
import barber_appointments.barber_appointments.model.Appointment;
import barber_appointments.barber_appointments.model.BarberService;
import barber_appointments.barber_appointments.repository.AppointmentRepository;
import barber_appointments.barber_appointments.repository.BarberServiceRepository;
import barber_appointments.barber_appointments.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        this.appointmentRepository.deleteById(id);
    }

    @Override
    public Optional<AppointmentClientResponseDTO> getAppointmentByToken(String token) {
        return Optional.empty();
    }

    @Override
    public List<AppointmentAdminResponseDTO> getAllAppointmentsForAdmin() {
        return List.of();
    }

    @Override
    public List<AppointmentClientResponseDTO> getAllAppointmentsForClient() {
        return List.of();
    }

    @Override
    public void confirmAppointment(String token) {

    }

    @Override
    public void cancelAppointment(String token) {

    }

    @Override
    public void rescheduleAppointment(String token, AppointmentRequestDTO request) {

    }
}
