package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.controller;

import lombok.RequiredArgsConstructor;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request.CrearEmpleadoRequest;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.CustomException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.EmpleadosResponse;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccempleado;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service.impl.EmpleadoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class EmpleadosController {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadosController.class);
    private final EmpleadoServiceImpl empleadoServiceImpl;

    @PostMapping("/empleado")
    @PreAuthorize("hasAnyRole('GERENTE_OFICINA', 'GERENTE_SUSCRIPTOR')")
    public ResponseEntity<?> crearEmpleado(@Valid @RequestBody CrearEmpleadoRequest request){
        logger.info("----> Iniciando creación de usuario");
        EmpleadosResponse response = empleadoServiceImpl.crearEmpleado(request);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/empleado")
    @PreAuthorize("hasAnyRole('GERENTE_OFICINA', 'GERENTE_SUSCRIPTOR')")
    public ResponseEntity<List<EmpleadosResponse>> obtenerEmpleados(){
        logger.info("----> Iniciando obtener usuarios");
        List<EmpleadosResponse> response = empleadoServiceImpl.obtenerEmpleados();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/empleado/{id}")
    @PreAuthorize("hasAnyRole('GERENTE_OFICINA', 'GERENTE_SUSCRIPTOR')")
    public ResponseEntity<Optional<Ccempleado>> actualizarEmpleado(@PathVariable Long id, @Valid @RequestBody CrearEmpleadoRequest empleado){
        logger.info("----> Iniciando actualizar usuario");
        Optional<Ccempleado> response = Optional.ofNullable(empleadoServiceImpl.actualizarEmpleado(id, empleado));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyRole('GERENTE_OFICINA', 'GERENTE_SUSCRIPTOR')")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Long id){
        logger.info("----> Iniciando eliminar usuario");
        empleadoServiceImpl.eliminarEmpleado(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}