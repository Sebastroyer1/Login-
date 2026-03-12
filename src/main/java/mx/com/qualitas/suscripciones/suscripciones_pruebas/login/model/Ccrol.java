package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CCROL")
public class Ccrol {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccrol_generator")
    @SequenceGenerator(name = "ccrol_generator", sequenceName = "CCROL_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre; // "Ejecutivo AO", "Gerente Oficina", etc.
}
