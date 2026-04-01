package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.exception.ClassificationException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.exception.FileProcessingException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.exception.SessionException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.CustomException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.ClaveAlreadyExistsException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.ExpiredTokenException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.InvalidTokenException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.RolesNotFoundException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.SecurityKeyNotFoundException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    // 400 - Validaciones @NotBlank, @NotNull, etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomException> handleValidation(MethodArgumentNotValidException ex) {
        String firstError = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Error de validacion");

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
        logger.error("----> Credenciales incorrectas: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // 403 - Acceso denegado
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomException> handleAccessDenied(AccessDeniedException ex) {
        CustomException error = new CustomException();
        error.setCode("ACCESS_DENIED");
        error.setMessage("No tienes permisos para acceder a este recurso");
        logger.error("----> Acceso denegado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // 404 - Usuario no encontrado
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomException> handleUserNotFound(UserNotFoundException ex) {
        CustomException error = new CustomException();
        error.setCode("USER_NOT_FOUND");
        error.setMessage(ex.getMessage());
        logger.error("----> Usuario no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 404 - Roles no encontrados
    @ExceptionHandler(RolesNotFoundException.class)
    public ResponseEntity<CustomException> handleRolesNotFound(RolesNotFoundException ex) {
        CustomException error = new CustomException();
        error.setCode("ROLES_NOT_FOUND");
        error.setMessage(ex.getMessage());
        logger.error("----> Roles no encontrados: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 409 - Clave duplicada
    @ExceptionHandler(ClaveAlreadyExistsException.class)
    public ResponseEntity<CustomException> handleClaveAlreadyExists(ClaveAlreadyExistsException ex) {
        CustomException error = new CustomException();
        error.setCode("CLAVE_ALREADY_EXISTS");
        error.setMessage(ex.getMessage());
        logger.error("----> Clave duplicada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 500 - Clave de seguridad no encontrada
    @ExceptionHandler(SecurityKeyNotFoundException.class)
    public ResponseEntity<CustomException> handleSecurityKeyNotFound(SecurityKeyNotFoundException ex) {
        CustomException error = new CustomException();
        error.setCode("SECURITY_KEY_NOT_FOUND");
        error.setMessage(ex.getMessage());
        logger.error("----> Clave de seguridad no encontrada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // 500 - ALARIS - Error al crear o cerrar sesión con Alaris
    @ExceptionHandler(SessionException.class)
    public ResponseEntity<CustomException> handleSessionException(SessionException ex) {
        CustomException error = new CustomException();
        error.setCode("SESSION_ERROR");
        error.setMessage(ex.getMessage());
        logger.error("----> Error de sesión Alaris: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // 500 - ALARIS - Error al procesar archivo
    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<CustomException> handleFileProcessing(FileProcessingException ex) {
        CustomException error = new CustomException();
        error.setCode("FILE_PROCESSING_ERROR");
        error.setMessage(ex.getMessage());
        logger.error("----> Error al procesar archivo: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // 500 - ALARIS - Error en clasificación
    @ExceptionHandler(ClassificationException.class)
    public ResponseEntity<CustomException> handleClassification(ClassificationException ex) {
        CustomException error = new CustomException();
        error.setCode("CLASSIFICATION_ERROR");
        error.setMessage(ex.getMessage());
        logger.error("----> Error en clasificación: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // 500 - Cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomException> handleGeneric(Exception ex) {
        CustomException error = new CustomException();
        error.setCode("INTERNAL_ERROR");
        error.setMessage("Ocurrio un error inesperado");
        logger.error("----> Error inesperado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
