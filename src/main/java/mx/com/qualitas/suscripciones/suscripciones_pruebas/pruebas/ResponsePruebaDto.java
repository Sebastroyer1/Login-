package mx.com.qualitas.suscripciones.suscripciones_pruebas.pruebas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePruebaDto {
    private String code;
    private String message;
    private String nombre;
    private String tipo;
    private String peso;
}
