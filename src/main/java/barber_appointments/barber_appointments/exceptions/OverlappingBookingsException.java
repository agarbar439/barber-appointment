package barber_appointments.barber_appointments.exceptions;

public class OverlappingBookingsException extends RuntimeException{
    public OverlappingBookingsException(String message) {
        super(message);
    }
}
