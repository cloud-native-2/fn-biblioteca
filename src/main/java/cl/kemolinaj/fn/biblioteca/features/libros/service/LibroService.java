package cl.kemolinaj.fn.biblioteca.features.libros.service;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroRqDto;
import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroRsDto;
import cl.kemolinaj.fn.biblioteca.shared.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LibroService {
    public void insertarLibro(LibroRqDto libroRqDto) {
        String sql = "INSERT INTO libros (nombre, editorial_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libroRqDto.getNombre());
            pstmt.setLong(2, libroRqDto.getEditorial());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar libro", e);
        }
    }

    public List<LibroRsDto> listarLibros() {
        List<LibroRsDto> listaLibroRsDto = new ArrayList<>();
        String sql = "SELECT id, nombre, editorial_id FROM libros";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LibroRsDto libro = new LibroRsDto(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("editorial_id")
                );
                listaLibroRsDto.add(libro);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al listar libros", e);
        }
        return listaLibroRsDto;
    }

    public boolean existeLibroPorId(Long idLibro) {
        String sql = "SELECT 1 FROM libros WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, idLibro);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al validar existencia del libro", e);
        }
    }

}
