package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request.CrearEmpleadoRequest;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.EmpleadosResponse;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccempleado;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface EmpleadoService {

    List<EmpleadosResponse> obtenerEmpleados();

    Optional<EmpleadosResponse> buscarEmpleado(String clave);

    EmpleadosResponse crearEmpleado(CrearEmpleadoRequest empleado);

    Ccempleado actualizarEmpleado(Long id, CrearEmpleadoRequest empleado);

    void eliminarEmpleado(Long id);

}
