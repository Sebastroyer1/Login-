package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.security;

import lombok.RequiredArgsConstructor;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.AuthResponse;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccempleado;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.repository.CcempleadoRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final CcempleadoRepository ccempleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Ccempleado empleado = ccempleadoRepository.findByClave(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + username));

        // Roles desde la BD
        List<GrantedAuthority> authorities = empleado.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre()))
                .collect(Collectors.toList());

        return User.builder()
                .username(empleado.getClave())
                .password(empleado.getContrasena())
                .authorities(authorities)
                .build();
    }
    public AuthResponse complementResponse(UserDetails userDetails, String jwt, String jwtRefresh) {
        Ccempleado empleado = ccempleadoRepository.findByClave(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        List<String> roles = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toList());

        return new AuthResponse(
                jwt,
                jwtRefresh,
                String.valueOf(empleado.getId()),
                empleado.getClave(),
                empleado.getNombre(),
                roles
        );
    }
}