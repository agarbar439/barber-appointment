package barber_appointments.barber_appointments.model;

import barber_appointments.barber_appointments.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User implements UserDetails {

    // Primary key auto-incremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique username, not null, max length 50
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    // Unique email, not null, max length 100
    @Column(unique = true, nullable = false, length = 100)
    public String email;


    // Not null password
    @Column(nullable = false)
    private String password; // guardada en BCrypt

    // User role, default ADMIN
    @Enumerated(EnumType.STRING)
    private  UserRole role = UserRole.ADMIN;


    // Implementation of UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum Role {
        ADMIN
    }
}