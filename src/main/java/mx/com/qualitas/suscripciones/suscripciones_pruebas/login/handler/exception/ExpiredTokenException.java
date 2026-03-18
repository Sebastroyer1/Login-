package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String message) { super(message); }
}
