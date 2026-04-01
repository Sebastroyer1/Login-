package mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class ClassifyRequest {
    private List<ResourceRef> resourceRefs; // lista de ref que espera Alaris
}
