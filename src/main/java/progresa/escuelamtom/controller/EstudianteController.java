package progresa.escuelamtom.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progresa.escuelamtom.dto.EstudianteDTO;
import progresa.escuelamtom.dto.Mensaje;
import progresa.escuelamtom.entity.Asignatura;
import progresa.escuelamtom.entity.Estudiante;
import progresa.escuelamtom.service.AsignaturaService;
import progresa.escuelamtom.service.EstudiantesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class EstudianteController {
    @Autowired
    private EstudiantesService estudiantesService;
    @Autowired
    private AsignaturaService asignaturaService;


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
    //crear una peticion para crear estudiante
    @PostMapping("/crear")
    public  ResponseEntity<?> crear(
            @RequestBody EstudianteDTO estudianteDTO
            ){
        if(StringUtils.isBlank(estudianteDTO.getNombre())){
            return new ResponseEntity(new Mensaje("El nombre del estudiante es obligatorio"),
                    HttpStatus.BAD_REQUEST);
        }
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(estudianteDTO.getNombre());
        List<Asignatura> asignaturas = new ArrayList<>();

        estudiantesService.save(estudiante);
        return new ResponseEntity(new Mensaje("Estudiante creado correctamente"),
                HttpStatus.CREATED);
    }
    //crear una peticion para asignar asignaturas a un estudiante
    @PostMapping("/asignarAsignatusAEstudiante/{idEstudiante}")
    public ResponseEntity<?> agregarAsignaturasAEstudiantes(
            @PathVariable Long idEstudiante,
            @RequestBody List<Long> asignaturasIds
    ){
        //validaciones
        //verificar si el estudiante existe
        if (!estudiantesService.existsById(idEstudiante)){
            return new ResponseEntity(new Mensaje("El estudiante con el id: "+ idEstudiante +" no existe"),
                    HttpStatus.BAD_REQUEST);
        }
        //validar que se proporcionaron los ids de las asignaturas
        if(asignaturasIds == null || asignaturasIds.isEmpty()){
            return new ResponseEntity(new Mensaje("Debe proporcionar al menos un id de asignatura"),
                    HttpStatus.BAD_REQUEST);
        }
        //obtener el estudiante
        Optional<Estudiante> estudianteOpt =
                estudiantesService.findById(idEstudiante);
        if(estudianteOpt.isEmpty()){
            return new ResponseEntity(new Mensaje("No es posible encontrar el estudiante"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //obtener la lista de asignaturas
        List<Asignatura> asignaturas = asignaturaService.findAllByIds(asignaturasIds);

        //validar que se encontraron asignaturas
        if(asignaturas.isEmpty() || asignaturas.size() != asignaturasIds.size()){
            return new ResponseEntity(new Mensaje("Algunas asignaturas no fueron encontradas"),
                    HttpStatus.BAD_REQUEST);
        }
        //asignar las asignaturas al estudiante
        Estudiante estudiante = new Estudiante();
        estudiante.getAsignaturas().addAll(asignaturas);
        estudiantesService.save(estudiante);
        return new ResponseEntity(new Mensaje("Asignaturas asignadas correctamente al estudiante"),
                HttpStatus.OK);
    }
    //  actualizar una asignatura
}
