package mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.response;

import lombok.Getter;
import lombok.Setter;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.request.DocumentInfo;

import java.util.List;

@Getter @Setter
public class ClassifyResponse {
    private List<DocumentInfo> documents;
    private List<PageInfoResponse> pages;
}
