package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.repository;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccempleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CcempleadoRepository extends JpaRepository<Ccempleado, Long> {

    Page<Ccempleado> findAll(Pageable pageable); //activos e inactivos
    Page<Ccempleado> findAllByEsactivoTrue(Pageable pageable); //solo activos

    Page<Ccempleado> findAllByNombreContainingIgnoreCase(Pageable pageable, String nombre);
    Page<Ccempleado> findAllByEsactivoTrueAndNombreContainingIgnoreCase(Pageable pageable, String nombre); //solo activos filtrado por nombre

    Optional<Ccempleado> findByClaveAndEsactivoTrue(String clave); //solo activos por clave

    // Buscar empleado por clave
    Optional<Ccempleado> findByClave(String clave); //solo por clave activos e inactivos

    // Verificar si existe un empleado con esa clave
    boolean existsByClave(String clave);



}
