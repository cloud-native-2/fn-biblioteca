package cl.kemolinaj.fn.biblioteca.features.libros.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LibroRqDto{
    public String nombre;
    public Long editorial;
}
