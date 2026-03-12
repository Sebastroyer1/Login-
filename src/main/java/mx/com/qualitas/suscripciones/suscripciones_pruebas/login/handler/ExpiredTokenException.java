package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String message) { super(message); }
}
