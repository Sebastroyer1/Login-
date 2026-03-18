package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.controller;

import lombok.RequiredArgsConstructor;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request.CrearEmpleadoRequest;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.CustomException;
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
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class EmpleadosController {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadosController.class);
    private final EmpleadoServiceImpl empleadoServiceImpl;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPdf(@RequestParam("file") MultipartFile file) {

        logger.info("----> Iniciando carga de archivo(s)");
        if (file.isEmpty()) {
            CustomException response = new CustomException();
            response.setCode("400");
            response.setMessage("Archivo no encontrado");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            byte[] bytes = file.getBytes();
            System.out.println("Bytes recibidos: " + bytes.length);

            java.util.Map<String, String> response = new java.util.HashMap<>();
            response.put("code", "200");
            response.put("message", "Archivo recibido correctamente");
            response.put("nombre", file.getOriginalFilename());
            response.put("tipo", file.getContentType());
            response.put("peso", String.valueOf(file.getSize()));
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/empleado")
    @PreAuthorize("hasAnyRole('GERENTE_OFICINA', 'GERENTE_SUSCRIPTOR','ADMINISTRADOR')")
    public ResponseEntity<Ccempleado> crearEmpleado(@Valid @RequestBody CrearEmpleadoRequest request){
        logger.info("----> Iniciando creación de usuario");
        Ccempleado response = empleadoServiceImpl.crearEmpleado(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/empleado")
    @PreAuthorize("hasAnyRole('GERENTE_OFICINA', 'GERENTE_SUSCRIPTOR','ADMINISTRADOR')")
    public ResponseEntity<List<Ccempleado>> obtenerEmpleados(){
        logger.info("----> Iniciando obtener usuarios");
        List<Ccempleado> response = empleadoServiceImpl.obtenerEmpleados();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/empleado/{id}")
    @PreAuthorize("hasAnyRole('GERENTE_OFICINA', 'GERENTE_SUSCRIPTOR','ADMINISTRADOR')")
    public ResponseEntity<Optional<Ccempleado>> actualizarEmpleado(@PathVariable Long id, @Valid @RequestBody CrearEmpleadoRequest empleado){
        logger.info("----> Iniciando actualizar usuario");
        Optional<Ccempleado> response = Optional.ofNullable(empleadoServiceImpl.actualizarEmpleado(id, empleado));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyRole('GERENTE_OFICINA', 'GERENTE_SUSCRIPTOR','ADMINISTRADOR')")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Long id){
        logger.info("----> Iniciando eliminar usuario");
        empleadoServiceImpl.eliminarEmpleado(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}