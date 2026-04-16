package cl.kemolinaj.fn.biblioteca.features.libros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroRsDto{
    public Long id;
    public String nombre;
    public String editorial;
}
