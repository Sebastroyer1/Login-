package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service;

import lombok.RequiredArgsConstructor;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request.AuthRequest;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.AuthResponse;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccempleado;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.repository.CcempleadoRepository;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final CcempleadoRepository ccempleadoRepository;

    public AuthResponse login(AuthRequest loginRequest) {
        // 1. Autenticar
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUnObfuscated(),
                        loginRequest.getUpObfuscated()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 2. Obtener rol desde las authorities
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .orElse("Sin rol");

        // 3. Crear tokens
        String accessToken = tokenProvider.createAccessToken(userDetails.getUsername(), role);
        String refreshToken = tokenProvider.createRefreshToken(userDetails.getUsername(), role);

        // 4. Buscar datos del empleado para la respuesta
        Ccempleado empleado = ccempleadoRepository.findByClave(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado"));

        // 5. Armar respuesta
        return new AuthResponse(
                accessToken,
                refreshToken,
                String.valueOf(empleado.getId()),
                empleado.getClave(),
                empleado.getName(),
                role
        );
    }

}
