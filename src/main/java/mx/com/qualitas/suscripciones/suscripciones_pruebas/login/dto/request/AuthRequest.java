package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter @Setter
public class AuthRequest {

    @NotBlank(message = "Falta campo requerido")
    private String unObfuscated; // clave del empleado

    @NotBlank(message = "Falta campo requerido")
    private String upObfuscated; // contraseña del empleado
}