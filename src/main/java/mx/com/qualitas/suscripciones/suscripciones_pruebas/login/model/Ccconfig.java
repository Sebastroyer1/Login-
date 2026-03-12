package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CCCONFIG")
public class Ccconfig {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccconfig_generator")
    @SequenceGenerator(name = "ccconfig_generator", sequenceName = "CCCONFIG_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "CLAVE", nullable = false, unique = true)
    private String clave;
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
}
