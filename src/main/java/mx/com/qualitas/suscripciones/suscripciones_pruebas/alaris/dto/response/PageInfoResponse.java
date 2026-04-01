package mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.response;

import lombok.Getter;
import lombok.Setter;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.request.PageRef;

@Getter @Setter
public class PageInfoResponse {
    private boolean matched;
    private String type;
    private PageRef pageRef;
    private double confidence;
}
