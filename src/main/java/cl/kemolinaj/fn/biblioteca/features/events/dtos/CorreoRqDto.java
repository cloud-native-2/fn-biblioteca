package cl.kemolinaj.fn.biblioteca.features.events.dtos;

import io.soabase.recordbuilder.core.RecordBuilder;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@RecordBuilder
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record CorreoRqDto(
        String correoDestino,
        String asunto,
        String mensaje
) {
}
