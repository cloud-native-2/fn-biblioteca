package cl.kemolinaj.fn.biblioteca.features.libros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroGraphQlDto {
    private Long id;
    private String nombre;
    private EditorialGraphQlDto editorial;
}
