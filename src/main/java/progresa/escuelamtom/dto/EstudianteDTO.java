package progresa.escuelamtom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDTO {
    private String nombre;
    private List<AsignaturaDTO> asignaturas;
}
