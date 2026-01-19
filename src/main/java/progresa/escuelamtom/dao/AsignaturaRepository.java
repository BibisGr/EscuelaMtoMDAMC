package progresa.escuelamtom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import progresa.escuelamtom.entity.Asignaturas;

import java.util.Optional;

public interface AsignaturaRepository extends JpaRepository<Asignaturas, Long> {
    public  boolean existsByTitulo(String titulo);
    Optional<Asignaturas> findByTitulo(String titulo);
}
