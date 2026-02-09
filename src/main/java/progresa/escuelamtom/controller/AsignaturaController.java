package progresa.escuelamtom.controller;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progresa.escuelamtom.dto.AsignaturaDTO;
import progresa.escuelamtom.dto.Mensaje;
import progresa.escuelamtom.entity.Asignatura;
import progresa.escuelamtom.service.AsignaturaService;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {
    @Autowired
    private AsignaturaService asignaturaService;

    //funciones CRUD para cursos
    //listar
    @GetMapping("/lista")
    public ResponseEntity<List<Asignatura>> listarAsignaturas(){
        List<Asignatura> list = asignaturaService.getAllAsignaturas();
        return new ResponseEntity(list,
                HttpStatus.OK);
    }
    //mostrar por id
    @GetMapping("/buscarPorId/{idAsignatura}")
    public ResponseEntity<?> buscarById(
            @PathVariable Long idAsignatura
    ){
        if(!asignaturaService.existsById(idAsignatura)){
            return new ResponseEntity(new Mensaje("No existe la asignatura con el iD:" + idAsignatura),
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(asignaturaService.findById(idAsignatura));
    }
    //eliminar
    @DeleteMapping("/delete/{idAsignatura}")
    public ResponseEntity<?> borrarById(
            @PathVariable Long idAsignatura
    ){
        if(!asignaturaService.existsById(idAsignatura)){
            return new ResponseEntity(
                    new Mensaje("No existe la asignatura con el iD:" + idAsignatura),
                    HttpStatus.BAD_REQUEST);
        }
        asignaturaService.deleteById(idAsignatura);
        return new ResponseEntity(
                new Mensaje("Asignatura eliminada correctamente"),
                HttpStatus.OK);
    }
    //crear
    @PostMapping("/crear")
    public ResponseEntity<?> crear(
            @RequestBody AsignaturaDTO  asignaturaDTO
    )
    {
        if(StringUtils.isBlank((asignaturaDTO.getTitulo()))){
            return new ResponseEntity(new Mensaje("El nombre de la asignatura es obligatorio"),
                    HttpStatus.BAD_REQUEST);
        }
        if(asignaturaService.existsByTitulo(asignaturaDTO.getTitulo())){
            return new ResponseEntity(new Mensaje("El title de la asignatura ya existe"),
                    HttpStatus.BAD_REQUEST);
        }
        Asignatura asignatura = new Asignatura();
        asignatura.setTitulo(asignaturaDTO.getTitulo());
        asignatura.setCreditos(asignaturaDTO.getCreditos());

        asignaturaService.save(asignatura);
        return new ResponseEntity(
                new Mensaje("Asignatura creada correctamente"),
                HttpStatus.CREATED);
    }
    //actualizar

}
