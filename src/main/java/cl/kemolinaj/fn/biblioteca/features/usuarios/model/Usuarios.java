package cl.kemolinaj.fn.biblioteca.features.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuarios {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "correo")
    private String correo;

    @Column(name = "nom_completo")
    private String nomCompleto;

    @Column(name = "run")
    private String run;
}
