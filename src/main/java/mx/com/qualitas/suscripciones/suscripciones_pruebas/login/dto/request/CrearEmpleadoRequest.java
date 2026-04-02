package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.*;
import java.util.List;

@Getter @Setter
public class CrearEmpleadoRequest {

    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombreCompleto;
    @Size(min = 3, max = 100,message = "La clave debe tener entre 3 y 100 caracteres")
    private String unObfuscated;  // clave del empleado
    @Size(min = 8, max = 33, message = "La contraseña debe tener entre 8 y 32 caracteres")
    private String upObfuscated;  // password del empleado
    @NotEmpty(message = "Debe asignar al menos un rol")
    private List<Long> roleIds;
}
