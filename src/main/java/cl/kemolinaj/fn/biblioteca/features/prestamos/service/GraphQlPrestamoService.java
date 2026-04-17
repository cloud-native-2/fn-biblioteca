package cl.kemolinaj.fn.biblioteca.features.prestamos.service;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.EditorialGraphQlDto;
import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroGraphQlDto;
import cl.kemolinaj.fn.biblioteca.features.prestamos.dtos.PrestamoGraphQlDto;
import cl.kemolinaj.fn.biblioteca.features.usuarios.dtos.UsuarioGraphQlDto;
import cl.kemolinaj.fn.biblioteca.shared.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GraphQlPrestamoService {
    public List<PrestamoGraphQlDto> listarPrestamosConDetalle(
            String username,
            String nombre,
            String email,
            String run
    ) {
        List<PrestamoGraphQlDto> prestamos = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT p.FOLIO, p.fecha_entrega, p.fecha_devolucion,
                   u.username as username, u.NOM_COMPLETO as usuario_nombre, u.CORREO as usuario_email, u.run as usuario_run,
                   l.id as libro_id, l.nombre as libro_nombre, e.NOMBRE as libro_editorial
              FROM prestamos p
              JOIN usuarios u ON p.username = u.username
              JOIN libros l ON p.ID_LIBRO = l.id
              JOIN EDITORIAL e ON l.EDITORIAL_ID = e.ID
             WHERE 1 = 1
            """);

        List<Object> params = new ArrayList<>();

        if (username != null && !username.isBlank()) {
            sql.append(" AND UPPER(u.username) = UPPER(?)");
            params.add(username);
        }

        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND UPPER(u.NOM_COMPLETO) LIKE UPPER(?)");
            params.add("%" + nombre + "%");
        }

        if (email != null && !email.isBlank()) {
            sql.append(" AND UPPER(u.CORREO) LIKE UPPER(?)");
            params.add("%" + email + "%");
        }

        if (run != null && !run.isBlank()) {
            sql.append(" AND UPPER(u.run) = UPPER(?)");
            params.add(run);
        }

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String fechaEntrega = rs.getObject("fecha_entrega", LocalDateTime.class) != null
                            ? rs.getObject("fecha_entrega", LocalDateTime.class).toString()
                            : null;
                    String fechaDevolucion = rs.getObject("fecha_devolucion", LocalDateTime.class) != null
                            ? rs.getObject("fecha_devolucion", LocalDateTime.class).toString()
                            : null;

                    PrestamoGraphQlDto prestamo = new PrestamoGraphQlDto(
                            rs.getLong("FOLIO"),
                            fechaEntrega,
                            fechaDevolucion,
                            new UsuarioGraphQlDto(
                                    rs.getString("username"),
                                    rs.getString("usuario_nombre"),
                                    rs.getString("usuario_email"),
                                    rs.getString("usuario_run")
                            ),
                            new LibroGraphQlDto(
                                    rs.getLong("libro_id"),
                                    rs.getString("libro_nombre"),
                                    new EditorialGraphQlDto(rs.getString("libro_editorial"))
                            )
                    );
                    prestamos.add(prestamo);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar préstamos con detalle filtrados por usuario", e);
        }

        return prestamos;
    }
}
