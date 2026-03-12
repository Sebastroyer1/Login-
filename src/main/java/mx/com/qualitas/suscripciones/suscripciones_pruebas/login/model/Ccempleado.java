package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CCEMPLEADO")
public class Ccempleado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccempleado_generator")
    @SequenceGenerator(name = "ccempleado_generator", sequenceName = "CCEMPLEADO_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NOMBRECOMPLETO")
    private String name;
    @Column(name = "CONTRASENA")
    private String password;
    private String clave;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CCEMPLEADO_ROL",
            joinColumns = @JoinColumn(name = "EMPLEADO_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROL_ID"))
    private Set<Ccrol> roles = new HashSet<>();  // para evitar roles repetidos se usa SET
}
