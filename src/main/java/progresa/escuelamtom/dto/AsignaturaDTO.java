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
public class AsignaturaDTO {
    private String titulo;
    private Integer creditos;
    private List<EstudianteDTO> alumnos;
}
