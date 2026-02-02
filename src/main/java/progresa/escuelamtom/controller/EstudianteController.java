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

    //funciones CRUD para cursos
    //listar
    @GetMapping("/lista")
    public ResponseEntity<List<Estudiante>> listarAlumno() {
        List<Estudiante> list = estudiantesService.getAllEstudiantes();
        return new ResponseEntity(list,
                HttpStatus.OK);
    }

    //mostrar por id
    @GetMapping("/buscarPorId/{idAlumno}")
    public ResponseEntity<?> buscarById(
            @PathVariable Long idAlumno
    ) {
        if (!estudiantesService.existsById(idAlumno)) {
            return new ResponseEntity(new Mensaje("No existe el alumno con el iD:" + idAlumno),
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(estudiantesService.findById(idAlumno));
    }

    //eliminar
    @DeleteMapping("/delete/{idAlumno}")
    public ResponseEntity<?> borrarById(
            @PathVariable Long idAlumno
    ) {
        if (!estudiantesService.existsById(idAlumno)) {
            return new ResponseEntity(
                    new Mensaje("No existe el curso con el iD:" + idAlumno),
                    HttpStatus.BAD_REQUEST);
        }
        estudiantesService.deleteById(idAlumno);
        return new ResponseEntity(
                new Mensaje("Curso eliminado correctamente"),
                HttpStatus.OK);
    }

    //crear
    @PostMapping("/crear")
    public ResponseEntity<?> crear(
            @RequestBody EstudianteDTO estudianteDTO
    ) {
        if (StringUtils.isBlank((estudianteDTO.getNombre()))) {
            return new ResponseEntity(new Mensaje("El nombre del alumno es obligatorio"),
                    HttpStatus.BAD_REQUEST);
        }
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(estudianteDTO.getNombre());
        List<Asignatura> asignaturas = new ArrayList<>();

        estudiantesService.save(estudiante);
        return new ResponseEntity(
                new Mensaje("Alumno creado correctamente"),
                HttpStatus.CREATED);
    }

    //funcion para asignar cursos a un alumno

    @PostMapping("/{idAlumno}/asignarCursos")
    public ResponseEntity<?> agregarCursosAAlumno(
            @PathVariable Long idAlumno,
            @RequestBody List<Long> asignaturasIds
    ){
        //validar que el alumno existe
        if(!estudiantesService.existsById(idAlumno)){
            return new ResponseEntity(
                    new Mensaje("No existe el alumno con el iD:" + idAlumno),
                    HttpStatus.BAD_REQUEST);
        }
        // validar que lo cursos proporcionados(length) concida con los cursos existentes
        if (asignaturasIds == null || asignaturasIds.isEmpty()){
            return new ResponseEntity(
                    new Mensaje("Debe proporcionar al menos un curso"),
                    HttpStatus.BAD_REQUEST);
        }
        //obtener el alumno
        Optional<Estudiante> estudianteOpt =
                estudiantesService.findById(idAlumno);
        if (estudianteOpt.isEmpty()){
            return new ResponseEntity(
                    new Mensaje("No se pudo encontrar el alumno con el iD:" + idAlumno),
                    HttpStatus.BAD_REQUEST);
        }
        //buscar los cursos por sus IDs para com[parar con el de alumnoOpt
        List<Asignatura> cursos = asignaturaService.findAllById(asignaturasIds);

        if(cursos.isEmpty() || cursos.size() != asignaturasIds.size())
        {
            return new ResponseEntity(
                    new Mensaje("Algunos de los cursos proporcionados no existen"),
                    HttpStatus.BAD_REQUEST);
        }

        //asignar los cursos al alumno
        Estudiante estudiante = estudianteOpt.get();
        estudiante.getAsignaturas().addAll(cursos);
        estudiantesService.save(estudiante);
        return new ResponseEntity(new Mensaje("Cursos agregados al alumno correctamente"),
                HttpStatus.OK);
    }

    //actualizar
    // que no se puedan agrega el mismo curso varia veces al mismo alumno

}
