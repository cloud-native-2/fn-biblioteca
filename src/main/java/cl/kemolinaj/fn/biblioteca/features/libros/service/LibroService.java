package cl.kemolinaj.fn.biblioteca.features.libros.service;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.EditorialGraphQlDto;
import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroGraphQlDto;
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

    public LibroGraphQlDto crearLibroConEditorial(String libroNombre, String editorialNombre) {
        String buscarEditorialSql = """
            SELECT id, nombre
              FROM editorial
             WHERE UPPER(nombre) = UPPER(?)
            """;

        String insertarEditorialSql = """
            INSERT INTO editorial (id, nombre)
            VALUES (seq_editorial.nextval, ?)
            """;

        String insertarLibroSql = """
            INSERT INTO libros (id, nombre, editorial_id)
            VALUES (SEQ_LIBROS.nextval,?, ?)
            """;

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            Long editorialId = null;

            try (PreparedStatement pstmt = conn.prepareStatement(buscarEditorialSql)) {
                pstmt.setString(1, editorialNombre);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        editorialId = rs.getLong("id");
                    }
                }
            }

            if (editorialId == null) {
                try (PreparedStatement pstmt = conn.prepareStatement(insertarEditorialSql, new String[]{"ID"})) {
                    pstmt.setString(1, editorialNombre);
                    pstmt.executeUpdate();

                    try (ResultSet keys = pstmt.getGeneratedKeys()) {
                        if (keys.next()) {
                            editorialId = keys.getLong(1);
                        }
                    }
                }
            }

            LibroGraphQlDto libroCreado = null;
            try (PreparedStatement pstmt = conn.prepareStatement(insertarLibroSql)) {
                pstmt.setString(1, libroNombre);
                pstmt.setLong(2, editorialId);
                pstmt.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            throw new RuntimeException("Error al crear libro con editorial", e);
        }
        return null;
    }

}
