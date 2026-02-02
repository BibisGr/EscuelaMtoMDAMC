package progresa.escuelamtom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progresa.escuelamtom.dao.AsignaturaRepository;
import progresa.escuelamtom.dao.EstudianteRepository;
import progresa.escuelamtom.entity.Asignatura;
import progresa.escuelamtom.entity.Estudiante;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstudiantesService {
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private AsignaturaRepository asignaturaRepository;

    //funciones crud
    //mostrar todos los estudiantes
    public List<Estudiante> getAllEstudiantes(){
        return estudianteRepository.findAll();
    }
    //mostrar estudiante por id
    //primero compruebo si existe
    public  boolean existsById(Long id){
        return estudianteRepository.existsById(id);
    }
    //luego lo busco (el estudiante)
    public Optional<Estudiante> findById(Long id){
        return estudianteRepository.findById(id);
    }
    //eliminar estudiante
    public  void deleteById(Long id){
        estudianteRepository.deleteById(id);
    }
    //crear estudiante y actualizar estudiante
    public void save(Estudiante estudiante) {
        estudianteRepository.save(estudiante);
    }

    public List<Asignatura> findAllById(List<Long> ids){
        return asignaturaRepository.findAllById(ids);
    }

}
