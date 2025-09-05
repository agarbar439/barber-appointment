package barber_appointments.barber_appointments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Global exception handler for the application
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle overlapping bookings
    @ExceptionHandler(OverlappingBookingsException.class)
    public ResponseEntity<Map<String, String>> handleOverlappingBookings(OverlappingBookingsException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT); // 409
    }

    // Handle service not found
    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleServiceNotFound(ServiceNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND); // 404
    }

    // Handle time slot unavailable
    @ExceptionHandler(TimeSlotUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleTimeSlotUnavailable(TimeSlotUnavailableException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT); // 409
    }

    // Handle working hours violations
    @ExceptionHandler(WorkingHoursException.class)
    public ResponseEntity<Map<String, String>> handleWorkingHours(WorkingHoursException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST); // 400
    }

    // Handle invalid confirmation tokens
    @ExceptionHandler(Exception.class) // fallback para cualquier otro error
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return buildErrorResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    // Utility method to build error responses
    private ResponseEntity<Map<String, String>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return new ResponseEntity<>(error, status);
    }
}
