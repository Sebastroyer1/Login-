package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter
public class CrearEmpleadoRequest {

    @NotBlank(message = "El campo no puede estar vacío")
    private String nombreCompleto;
    @NotBlank(message = "El campo no puede estar vacío")
    private String unObfuscated;  // clave del empleado
    @NotBlank(message = "El campo no puede estar vacío")
    private String upObfuscated;  // password del empleado
    @NotEmpty(message = "Debe asignar al menos un rol")
    private List<Long> roleIds;
}
