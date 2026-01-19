package progresa.escuelamtom.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "asignatura")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Asignaturas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "creditos")
    private Integer creditos;

    @ManyToMany(mappedBy = "asignaturas")
    @JsonBackReference
    private Set<Estudiante> estudiantes = new HashSet<>();
}
