package barber_appointments.barber_appointments.repository;

import barber_appointments.barber_appointments.model.BarberService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberServiceRepository extends JpaRepository<BarberService, Long> {

}
