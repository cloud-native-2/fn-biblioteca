package cl.kemolinaj.fn.biblioteca.features.prestamos.service;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroRsDto;
import cl.kemolinaj.fn.biblioteca.features.libros.service.LibroService;
import cl.kemolinaj.fn.biblioteca.features.prestamos.dtos.PrestamoRsDto;
import cl.kemolinaj.fn.biblioteca.features.prestamos.dtos.PretamoRqDto;
import cl.kemolinaj.fn.biblioteca.features.usuarios.dtos.UsuarioDto;
import cl.kemolinaj.fn.biblioteca.features.usuarios.service.UsuarioService;
import cl.kemolinaj.fn.biblioteca.shared.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PrestamoService {
    private final UsuarioService usuarioService = new UsuarioService();
    private final LibroService libroService = new LibroService();

    public void insertarPrestamo(PretamoRqDto prestamoRqDto) {
        // Validar existencia de usuario
        if (!usuarioService.existeUsuarioPorUsername(prestamoRqDto.getUsername())) {
            throw new IllegalArgumentException("Usuario no existe: " + prestamoRqDto.getUsername());
        }
        // Validar existencia de libro
        if (!libroService.existeLibroPorId(prestamoRqDto.getIdLibro())) {
            throw new IllegalArgumentException("Libro no existe con id: " + prestamoRqDto.getIdLibro());
        }

        String sql = "INSERT INTO prestamos (fecha_entrega, username, id_libro) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, LocalDateTime.now());
            pstmt.setString(2, prestamoRqDto.getUsername());
            pstmt.setLong(3, prestamoRqDto.getIdLibro());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar el préstamo", e);
        }
    }

    public List<PrestamoRsDto> listarPrestamos() {
        List<PrestamoRsDto> listaPrestamos = new ArrayList<>();
        String sql = """                      
            SELECT * FROM prestamos
            """;
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PrestamoRsDto prestamoRsDto = new PrestamoRsDto(
                        rs.getLong("folio"),
                        rs.getObject("fecha_entrega", LocalDateTime.class),
                        rs.getObject("fecha_devolucion", LocalDateTime.class),
                        null, null
                );
                listaPrestamos.add(prestamoRsDto);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar préstamos", e);
        }
        return listaPrestamos;
    }

}
