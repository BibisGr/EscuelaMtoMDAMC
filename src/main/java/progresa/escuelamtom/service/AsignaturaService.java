package progresa.escuelamtom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progresa.escuelamtom.dao.AsignaturaRepository;
import progresa.escuelamtom.entity.Asignatura;
import progresa.escuelamtom.entity.Estudiante;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AsignaturaService {
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    //funciones crud
    //mostrar todas las asignaturas
    public List<Asignatura> getAllAsignaturas(){
        return asignaturaRepository.findAll();
    }
    //mostrar la asignatura por id
    //primero compruebo si existe
    public  boolean existsById(Long id){
        return asignaturaRepository.existsById(id);
    }
    //luego lo busco (la asignatura)
    public Optional<Asignatura> findById(Long id){
        return asignaturaRepository.findById(id);
    }
    //mostrar la asignatura por nombre
    //primero compruebo si existe
    public  boolean existsByTitulo(String titulo){
        return asignaturaRepository.existsByTitulo(titulo);
    }
    //luego lo busco (la asignatura)
    public Optional<Asignatura> findByTitulo(String titulo){
        return asignaturaRepository.findByTitulo(titulo);
    }
    //eliminar la asignatura
    public  void deleteById(Long id){
        asignaturaRepository.deleteById(id);
    }
    //crear estudiante y actualizar estudiante
    public void save(Asignatura asignatura) {
        asignaturaRepository.save(asignatura);
    }

}
