package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception;

public class SecurityKeyNotFoundException extends RuntimeException {
    public SecurityKeyNotFoundException(String message) {
        super(message);
    }
}