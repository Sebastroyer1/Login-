package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.repository;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model.Ccrol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CcrolRepository extends JpaRepository<Ccrol, Long> {
    List<Ccrol> findAllById(Iterable<Long> ids);
}
