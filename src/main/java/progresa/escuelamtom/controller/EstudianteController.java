package progresa.escuelamtom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progresa.escuelamtom.dto.Mensaje;
import progresa.escuelamtom.entity.Asignatura;
import progresa.escuelamtom.entity.Estudiante;
import progresa.escuelamtom.service.EstudiantesService;

import java.util.List;

@RestController
@RequestMapping
public class EstudianteController {
    private EstudiantesService estudiantesService;
    //    funciones Crud
    //  mostrar todas los estudiantes
    @GetMapping("/lista")
    public ResponseEntity<List<Estudiante>> List(){
        List<Estudiante> list = estudiantesService.getAllEstudiantes();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //  mostrar un estudiante por id
    @GetMapping("/buscarById/{id}")
    public  ResponseEntity<?> buscarById(
            @PathVariable Long id
    ) {
        if(!estudiantesService.existsById(id)){
            return new ResponseEntity(new
                    Mensaje("El estudiante con el id: "+ id +" no existe"),
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(estudiantesService.findById(id));
    }
    //  eliminar un estudiante
    @DeleteMapping("/detele/{id}")
    public  ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        if(!estudiantesService.existsById(id)){
            return new ResponseEntity(new
                    Mensaje("El estudiante con el id: "+ id +" no existe"),
                    HttpStatus.BAD_REQUEST);
        }
        estudiantesService.deleteById(id);
        return new ResponseEntity(new Mensaje("Asignatura eliminada correctamente"),HttpStatus.OK);
    }
    //  crear una asignatura
    //  actualizar una asignatura
}
