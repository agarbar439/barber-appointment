package barber_appointments.barber_appointments;

import barber_appointments.barber_appointments.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BarberAppointmentsBackendApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	public void testPasswordMatching() {
		// Suponiendo que esta es la contraseña cifrada que tienes en la base de datos
		String passwordFromDB = "$2a$10$7y3JhU3MlvX2Y1m3XjDre.CXyXb9TKrjmABpHLoCh37SMX4UPc9Zq";
		// La contraseña en claro que estás verificando
		String rawPassword = "1234";

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean matches = encoder.matches(rawPassword, passwordFromDB);
		assertTrue(matches); // Esto debería devolver true si las contraseñas coinciden
	}


}
