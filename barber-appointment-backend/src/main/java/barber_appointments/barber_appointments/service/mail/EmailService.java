package barber_appointments.barber_appointments.service.mail;

import barber_appointments.barber_appointments.model.Appointment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// Service for sending emails related to appointments
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // Constructor injection of JavaMailSender
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Send appointment confirmation email with action links
    public void sendAppointmentEmail(Appointment appointment) {
        String subject = "Confirmation of your appointment at Hair Barber";

        String text = String.format(
                "Hello %s,\n\n" +
                        "Your appointment has been registered with the following details:\n" +
                        "- Service: %s\n" +
                        "- Date: %s\n" +
                        "- Hour: %s\n\n" +
                        "Please confirm or manage your appointment using the following links:\n" +
                        "✔ Confirm: http://localhost:8080/api/appointments/confirm?token=%s\n" +
                        "❌ Cancel: http://localhost:8080/api/appointments/cancel?token=%s\n" +
                        "🔄 Reschedule: http://localhost:8080/api/appointments/reschedule?token=%s\n\n" +
                        "Thank you for placing your trust in us.",
                appointment.getCustomerName(),
                appointment.getService().getName(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getConfirmationToken(),
                appointment.getConfirmationToken(),
                appointment.getConfirmationToken()
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@hairbarber.com");
        message.setTo(appointment.getCustomerEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    // Message for cancellation
    public void sendCancellationEmail(Appointment appointment) {
        String subject = "Cancellation of your appointment at Hair Barber";

        String text = String.format(
                "Hello %s,\n\n" +
                        "Your appointment for the service '%s' on %s at %s has been successfully cancelled.\n\n" +
                        "We hope to see you again soon.\n\n" +
                        "Thank you for placing your trust in us.",
                appointment.getCustomerName(),
                appointment.getService().getName(),
                appointment.getDate(),
                appointment.getTime()
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@hairbarber.com");
        message.setTo(appointment.getCustomerEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);

    }

    // Message for rescheduling
    public void sendRescheduleEmail(Appointment appointment) {
        String subject = "Rescheduling of your appointment at Hair Barber";

        String text = String.format(
                "Hello %s,\n\n" +
                        "Your appointment has been rescheduled with the following details:\n" +
                        "- Service: %s\n" +
                        "- Date: %s\n" +
                        "- Hour: %s\n\n" +
                        "Please confirm or manage your appointment using the following links:\n" +
                        "✔ Confirm: http://localhost:8080/api/appointments/confirm?token=%s\n" +
                        "❌ Cancel: http://localhost:8080/api/appointments/cancel?token=%s\n" +
                        "🔄 Reschedule: http://localhost:8080/api/appointments/reschedule?token=%s\n\n" +
                        "Thank you for placing your trust in us.",
                appointment.getCustomerName(),
                appointment.getService().getName(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getConfirmationToken(),
                appointment.getConfirmationToken(),
                appointment.getConfirmationToken()
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@hairbarber.com");
        message.setTo(appointment.getCustomerEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);

    }

    // Appointment cancellation by admin
    public void sendAdminCancellationEmail(Appointment appointment) {
        String subject = "Cancellation of your appointment at Hair Barber";
        String text = String.format(
                "Hello %s,\n\n" +
                        "We regret to inform you that your appointment for the service '%s' on %s at %s has been cancelled by the administration.\n\n" +
                        "We apologize for any inconvenience this may cause and hope to see you again soon.\n\n" +
                        "Thank you for placing your trust in us.",
                appointment.getCustomerName(),
                appointment.getService().getName(),
                appointment.getDate(),
                appointment.getTime()
        );
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@hairbarber.com");
        message.setTo(appointment.getCustomerEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);

    }

}