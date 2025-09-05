package barber_appointments.barber_appointments.mapper;

import barber_appointments.barber_appointments.dto.BarberServiceDTO;
import barber_appointments.barber_appointments.model.BarberService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BarberServiceMapper {

    // Convert entity to DTO
    public BarberServiceDTO toDTO(BarberService barberService) {
        BarberServiceDTO dto = new BarberServiceDTO();
        dto.setId(barberService.getId());
        dto.setName(barberService.getName());
        dto.setDescription(barberService.getDescription());
        dto.setPrice(barberService.getPrice());
        dto.setDurationMinutes(barberService.getDurationMinutes());
        return dto;
    }

    // Convert DTO to entity
    public BarberService toEntity(BarberServiceDTO dto) {
        BarberService barberService = new BarberService();
        barberService.setId(dto.getId());
        barberService.setName(dto.getName());
        barberService.setDescription(dto.getDescription());
        barberService.setPrice(dto.getPrice());
        barberService.setDurationMinutes(dto.getDurationMinutes());
        return barberService;
    }

    // Convert list of entities to list of DTOs
    public List<BarberServiceDTO> toDTOList(List<BarberService> barberServices) {
        return barberServices.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Convert list of DTOs to list of entities
    public List<BarberService> toEntityList(List<BarberServiceDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
