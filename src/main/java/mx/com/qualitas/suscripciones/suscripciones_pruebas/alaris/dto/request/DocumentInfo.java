package mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class DocumentInfo {
    private String formType;
    private Long formTypeId;
    private List<PageRef> pages;
}
