package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service.impl;

import lombok.RequiredArgsConstructor;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request.CrearEmpleadoRequest;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.ClaveAlreadyExistsException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.RolesNotFoundException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.UserNotFoundException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccempleado;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccrol;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.repository.CcempleadoRepository;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.repository.CcrolRepository;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.controller.EmpleadosController;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadosController.class);
    private final CcempleadoRepository ccempleadoRepository;
    private final CcrolRepository ccrolRepository;

    @Override
    public List<Ccempleado> obtenerEmpleados() {

        List<Ccempleado> empleados = ccempleadoRepository.findAllByEsactivoTrue();
        if(empleados.isEmpty()){
            return Collections.emptyList();
        }else {
            return empleados;
        }
    }

    @Override
    public Optional<Ccempleado> buscarEmpleado(String clave) {

        if(ccempleadoRepository.existsByClave(clave)){
            throw new UserNotFoundException("Usuario no encontrado: " + clave);
        }else {
            Optional<Ccempleado> empleado = ccempleadoRepository.findByClave(clave);
            Ccempleado response = new Ccempleado();
            response.setId(empleado.get().getId());
            response.setNombre(empleado.get().getNombre());
            response.setClave(empleado.get().getClave());
            response.setContrasena(empleado.get().getContrasena());
            response.setRoles(empleado.get().getRoles());
            return Optional.of(response);
        }
    }

    @Override
    public Ccempleado crearEmpleado(CrearEmpleadoRequest request) {
        // 1. Verificar que la clave no esté en uso
        if (ccempleadoRepository.existsByClave(request.getUnObfuscated())) {
            throw new ClaveAlreadyExistsException("La clave ya está en uso: " + request.getUnObfuscated());
        }
        // 2. Buscar los roles por los ids recibidos
        List<Ccrol> roles = ccrolRepository.findAllById(request.getRoleIds());
        if (roles.isEmpty()) {
            throw new RolesNotFoundException("Roles no válidos");
        }
        // 3. Crear el empleado
        Ccempleado empleado = new Ccempleado();
        empleado.setNombre(request.getNombreCompleto());
        empleado.setClave(request.getUnObfuscated());
        empleado.setContrasena(request.getUpObfuscated());
        empleado.setRoles(new HashSet<>(roles));

        logger.info("----> Empleado creado ");
       return ccempleadoRepository.save(empleado);

    }

    @Override
    public Ccempleado actualizarEmpleado(Long id, CrearEmpleadoRequest empleado) {
        Ccempleado empleadoActualizado = ccempleadoRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + id));

        empleadoActualizado.setNombre(empleado.getNombreCompleto());
        empleadoActualizado.setClave(empleado.getUnObfuscated());
        empleadoActualizado.setContrasena(empleado.getUpObfuscated());
        empleadoActualizado.setRoles(new HashSet<>(ccrolRepository.findAllById(empleado.getRoleIds())));

        logger.info("----> Empleado actualizado ");
        return ccempleadoRepository.save(empleadoActualizado);
    }

    @Override
    public void eliminarEmpleado(Long id) {
        Ccempleado empleadoActualizado = ccempleadoRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + id));
        empleadoActualizado.setEsactivo(false);
        ccempleadoRepository.save(empleadoActualizado);
        logger.info("----> Empleado eliminado (estado: false) ");
    }
}
