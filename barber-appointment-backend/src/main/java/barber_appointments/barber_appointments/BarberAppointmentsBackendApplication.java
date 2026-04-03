package barber_appointments.barber_appointments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BarberAppointmentsBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(BarberAppointmentsBackendApplication.class, args);

		System.out.println("Barber Appointments Backend is running!");
	}
}
