package barber_appointments.barber_appointments.controller;


import barber_appointments.barber_appointments.dto.AuthDto;
import barber_appointments.barber_appointments.security.JwtUtil;
import barber_appointments.barber_appointments.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, AuthService authService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    // Login endpoint for authenticating users and issuing JWT tokens.
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDto.LoginRequest request, HttpServletResponse response) {
        try {
            AuthDto.AuthResponse authResponse = authService.login(request);

            // 1. Crear la Cookie con el Token
            ResponseCookie cookie = ResponseCookie.from("accessToken", authResponse.getToken())
                    .httpOnly(true)
                    .secure(false)      // Change to true in production with HTTPS
                    .path("/")
                    .maxAge(3600)      // 1 hour
                    .sameSite("Lax")   // Protecction against CSRF
                    .build();

            // 2. Add the cookie to the response header and return the auth response body
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(authResponse);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid username or password"));
        }
    }

    // Register endpoint for creating new users.
    @PostMapping ("/admin/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@Valid @RequestBody AuthDto.RegisterRequest request) {
        try {
            AuthDto.AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken() {
        // Si el token es válido, Spring Security permite acceder a este endpoint
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", true);
        return ResponseEntity.ok(response);
    }
}
