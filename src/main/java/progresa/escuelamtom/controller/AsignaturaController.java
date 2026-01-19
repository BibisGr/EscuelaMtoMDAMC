package progresa.escuelamtom.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progresa.escuelamtom.dto.Mensaje;
import progresa.escuelamtom.entity.Asignatura;
import progresa.escuelamtom.service.AsignaturaService;

import java.util.List;

@RestController
@RequestMapping
public class AsignaturaController {
    @Autowired
    private AsignaturaService asignaturaService;

//    funciones Crud
    //  mostrar todas las asignaturas
    @GetMapping("/lista")
    public ResponseEntity<List<Asignatura>> List(){
        List<Asignatura> list = asignaturaService.getAllAsignaturas();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    //  mostrar una asignatura por id
    @GetMapping("/buscarById/{id}")
    public  ResponseEntity<?> buscarById(
            @PathVariable Long id
    ) {
        if(!asignaturaService.existsById(id)){
            return new ResponseEntity(new
                    Mensaje("La Asignatura con el id: "+ id +" no existe"),
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(asignaturaService.findById(id));
    }
    //  eliminar una asignatura
    @DeleteMapping("/detele/{id}")
    public  ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        if(!asignaturaService.existsById(id)){
            return new ResponseEntity(new
                    Mensaje("La Asignatura con el id: "+ id +" no existe"),
                    HttpStatus.BAD_REQUEST);
        }
        asignaturaService.deleteById(id);
        return new ResponseEntity(new Mensaje("Asignatura eliminada correctamente"),HttpStatus.OK);
    }
    //  crear una asignatura
    //  actualizar una asignatura

}
