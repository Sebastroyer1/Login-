package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.request.CrearEmpleadoRequest;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccempleado;

import java.util.List;
import java.util.Optional;


public interface EmpleadoService {

    List<Ccempleado> obtenerEmpleados();

    Optional<Ccempleado> buscarEmpleado(String clave);

    Ccempleado crearEmpleado(CrearEmpleadoRequest empleado);

    Ccempleado actualizarEmpleado(Long id, CrearEmpleadoRequest empleado);

    void eliminarEmpleado(Long id);


}
