package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception;

public class ClaveAlreadyExistsException extends RuntimeException {
    public ClaveAlreadyExistsException(String message) { super(message); }
}