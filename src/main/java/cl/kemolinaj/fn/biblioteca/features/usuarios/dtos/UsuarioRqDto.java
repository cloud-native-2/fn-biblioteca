package cl.kemolinaj.fn.biblioteca.features.usuarios.dtos;

import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@RecordBuilder
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record UsuarioRqDto(
        @NotNull
        String username,
        @NotNull
        String correo,
        @NotNull
        String nomCompleto,
        @NotNull
        String run
) {
}
