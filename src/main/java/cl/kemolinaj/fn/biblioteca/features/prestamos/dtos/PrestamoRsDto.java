package cl.kemolinaj.fn.biblioteca.features.prestamos.dtos;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroRsDto;
import cl.kemolinaj.fn.biblioteca.features.usuarios.dtos.UsuarioDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.soabase.recordbuilder.core.RecordBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PrestamoRsDto{
    public Long folio;
    public LocalDateTime fechaEntrega;
    public LocalDateTime fechaDevolucion;
    public UsuarioDto username;
    public LibroRsDto libro;
}
