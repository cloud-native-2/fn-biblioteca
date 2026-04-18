package cl.kemolinaj.fn.biblioteca.features.libros.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LibroRsDto{
    public Long id;
    public String nombre;
    public String editorial;
}
