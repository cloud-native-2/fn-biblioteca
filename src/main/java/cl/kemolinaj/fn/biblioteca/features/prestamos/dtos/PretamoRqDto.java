package cl.kemolinaj.fn.biblioteca.features.prestamos.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PretamoRqDto{
    public String username;
    public Long idLibro;
}
