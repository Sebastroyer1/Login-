package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomException {
    private String code;
    private String message;
}
