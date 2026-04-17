package cl.kemolinaj.fn.biblioteca.features.prestamos.dtos;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroGraphQlDto;
import cl.kemolinaj.fn.biblioteca.features.usuarios.dtos.UsuarioGraphQlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoGraphQlDto {
    private Long folio;
    private String fechaEntrega;
    private String fechaDevolucion;
    private UsuarioGraphQlDto usuario;
    private LibroGraphQlDto libro;
}
