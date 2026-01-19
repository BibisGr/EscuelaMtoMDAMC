package progresa.escuelamtom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import progresa.escuelamtom.entity.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}
