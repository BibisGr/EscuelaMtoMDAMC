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
    @PostMapping("/crear")
    public ResponseEntity<?> crear(
            @RequestBody AsignaturaDTO asignaturaDTO
            ){
        //validaciones
        // el titulo no puede estar vacio
        if(StringUtils.isBlank(asignaturaDTO.getTitulo())){
            return new ResponseEntity(new Mensaje("El titulo es obligatorio"),
                    HttpStatus.BAD_REQUEST);
        }
        // el titulo no puede existir
        if(asignaturaService.existsByTitulo(asignaturaDTO.getTitulo())){
            return new ResponseEntity(new Mensaje("La asignatura con el nombre: "+ asignaturaDTO.getTitulo() + " ya existe"),
                    HttpStatus.BAD_REQUEST);
        }

        Asignatura asignatura = new Asignatura();
        asignatura.setTitulo(asignaturaDTO.getTitulo());
        asignatura.setCreditos(asignaturaDTO.getCreditos());

        asignaturaService.save(asignatura);
        return new ResponseEntity(new Mensaje("Asignatura creada correctamente"),
                HttpStatus.CREATED);
    }
    //  actualizar una asignatura
    // pasariamos el id por la url y el resto de datos por el body

}
