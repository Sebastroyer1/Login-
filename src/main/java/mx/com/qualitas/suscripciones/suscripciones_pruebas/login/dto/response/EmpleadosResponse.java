package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service.impl.HtmlEscapeSerializer;

import java.util.List;

@Data
public class EmpleadosResponse {

    private Long id;

    @JsonSerialize(using = HtmlEscapeSerializer.class)
    private String nombre;

    @JsonSerialize(using = HtmlEscapeSerializer.class)
    private String clave;

    private Boolean esactivo;

    @JsonSerialize(contentUsing = HtmlEscapeSerializer.class)
    private List<String> roles;

}
