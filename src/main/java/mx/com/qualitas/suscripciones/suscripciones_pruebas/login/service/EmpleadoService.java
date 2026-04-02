package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request.CrearEmpleadoRequest;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.EmpleadosResponse;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccempleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface EmpleadoService {

    Page<EmpleadosResponse> obtenerEmpleadosActivos(Pageable pageable,String nombre);
    Page<EmpleadosResponse> obtenerEmpleados(Pageable pageable,String nombre);

    Optional<EmpleadosResponse> buscarEmpleado(String clave);

    EmpleadosResponse crearEmpleado(CrearEmpleadoRequest empleado);

    Ccempleado actualizarEmpleado(Long id, CrearEmpleadoRequest empleado);

    void eliminarEmpleado(Long id);

}
