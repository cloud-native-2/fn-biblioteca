package cl.kemolinaj.fn.biblioteca.features.usuarios.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioGraphQlDto {
    private String username;
    private String nombre;
    private String email;
    private String run;
}
