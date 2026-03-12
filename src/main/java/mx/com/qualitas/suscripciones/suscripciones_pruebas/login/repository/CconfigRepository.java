package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.repository;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccconfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CconfigRepository extends JpaRepository<Ccconfig, Long> {
    // Buscar configuración por clave
    Optional<Ccconfig> findByClave(String clave);

}
