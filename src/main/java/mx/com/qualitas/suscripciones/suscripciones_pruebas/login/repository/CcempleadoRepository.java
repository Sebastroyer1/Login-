package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.repository;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccempleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CcempleadoRepository extends JpaRepository<Ccempleado, Long> {

    // Buscar empleado por clave
    Optional<Ccempleado> findByClave(String clave);
    // Verificar si existe un empleado con esa clave
    boolean existsByClave(String clave);

    List<Ccempleado> findAllByEsactivoTrue();

}
