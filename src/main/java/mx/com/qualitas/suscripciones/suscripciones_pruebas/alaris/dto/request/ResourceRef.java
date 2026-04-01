package mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResourceRef {
    private String ref;  // ref del archivo que regresa Alaris
}
