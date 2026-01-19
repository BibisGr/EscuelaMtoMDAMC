package progresa.escuelamtom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import progresa.escuelamtom.entity.Asignatura;

import java.util.Optional;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    public  boolean existsByTitulo(String titulo);
    Optional<Asignatura> findByTitulo(String titulo);
}
