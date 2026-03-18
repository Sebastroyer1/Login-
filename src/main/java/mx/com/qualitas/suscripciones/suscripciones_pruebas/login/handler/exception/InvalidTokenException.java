package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) { super(message); }
}
