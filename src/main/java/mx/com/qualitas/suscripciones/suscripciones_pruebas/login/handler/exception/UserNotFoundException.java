package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) { super(message); }
}
