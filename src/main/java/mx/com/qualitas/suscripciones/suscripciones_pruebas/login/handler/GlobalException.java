package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.CustomException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.ClaveAlreadyExistsException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.ExpiredTokenException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.InvalidTokenException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.RolesNotFoundException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.SecurityKeyNotFoundException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.UserNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    // 400 - Validaciones @NotBlank, @NotNull, etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomException> handleValidation(MethodArgumentNotValidException ex) {
        String firstError = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Error de validación");

        CustomException error = new CustomException();
        error.setCode("VALIDATION_ERROR");
        error.setMessage(firstError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 401 - Token expirado
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<CustomException> handleExpiredToken(ExpiredTokenException ex) {
        CustomException error = new CustomException();
        error.setCode("TOKEN_EXPIRED");
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // 401 - Token inválido
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<CustomException> handleInvalidToken(InvalidTokenException ex) {
        CustomException error = new CustomException();
        error.setCode("TOKEN_INVALID");
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // 401 - Credenciales incorrectas
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomException> handleBadCredentials(BadCredentialsException ex) {
        CustomException error = new CustomException();
        error.setCode("BAD_CREDENTIALS");
        error.setMessage("Credenciales incorrectas: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // 403 - Acceso denegado
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomException> handleAccessDenied(AccessDeniedException ex) {
        CustomException error = new CustomException();
        error.setCode("ACCESS_DENIED");
        error.setMessage("No tienes permisos para acceder a este recurso");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // 404 - Usuario no encontrado
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomException> handleUserNotFound(UserNotFoundException ex) {
        CustomException error = new CustomException();
        error.setCode("USER_NOT_FOUND");
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 404 - Roles no encontrados
    @ExceptionHandler(RolesNotFoundException.class)
    public ResponseEntity<CustomException> handleRolesNotFound(RolesNotFoundException ex) {
        CustomException error = new CustomException();
        error.setCode("ROLES_NOT_FOUND");
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 409 - Clave duplicada
    @ExceptionHandler(ClaveAlreadyExistsException.class)
    public ResponseEntity<CustomException> handleClaveAlreadyExists(ClaveAlreadyExistsException ex) {
        CustomException error = new CustomException();
        error.setCode("CLAVE_ALREADY_EXISTS");
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 500 - Clave de seguridad no encontrada
    @ExceptionHandler(SecurityKeyNotFoundException.class)
    public ResponseEntity<CustomException> handleSecurityKeyNotFound(SecurityKeyNotFoundException ex) {
        CustomException error = new CustomException();
        error.setCode("SECURITY_KEY_NOT_FOUND");
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // 500 - Cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomException> handleGeneric(Exception ex) {
        CustomException error = new CustomException();
        error.setCode("INTERNAL_ERROR");
        error.setMessage("Ocurrió un error inesperado");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
