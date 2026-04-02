package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service;

import lombok.RequiredArgsConstructor;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request.AuthRequest;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.AuthResponse;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.UserNotFoundException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccempleado;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.repository.CcempleadoRepository;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        // 2. Obtener roles desde las authorities
        List<String> roles = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toList());

        // 3. Crear tokens
        String accessToken = tokenProvider.createAccessToken(userDetails.getUsername(), roles);
        String refreshToken = tokenProvider.createRefreshToken(userDetails.getUsername(), roles);

        // 4. Buscar datos del empleado para la respuesta
        Ccempleado empleado = ccempleadoRepository.findByClaveAndEsactivoTrue(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Empleado no encontrado"));

        // 5. Armar respuesta
        return new AuthResponse(
                accessToken,
                refreshToken,
                String.valueOf(empleado.getId()),
                empleado.getClave(),
                empleado.getNombre(),
                roles
        );
    }

}
