package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AuthResponse {

    private String jwt;
    private String jwtRefresh;
    private String uiObfuscated;  // id del empleado
    private String unObfuscated;  // clave del empleado
    private String name;          // nombre completo
    private String role;          // rol del empleado
}
