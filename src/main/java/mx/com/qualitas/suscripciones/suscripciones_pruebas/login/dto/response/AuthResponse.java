package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service.impl.HtmlEscapeSerializer;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class AuthResponse {

    private String jwt;
    private String jwtRefresh;
    private String uiObfuscated;  // id del empleado
    @JsonSerialize(using = HtmlEscapeSerializer.class)
    private String unObfuscated;  // clave del empleado
    @JsonSerialize(using = HtmlEscapeSerializer.class)
    private String nombre;          // nombre completo
    private List<String> roles;    // roles del empleado
}
