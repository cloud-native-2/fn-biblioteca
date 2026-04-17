package cl.kemolinaj.fn.biblioteca.features.usuarios.service;

import cl.kemolinaj.fn.biblioteca.features.usuarios.dtos.UsuarioDto;
import cl.kemolinaj.fn.biblioteca.features.usuarios.dtos.UsuarioGraphQlDto;
import cl.kemolinaj.fn.biblioteca.shared.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    public List<UsuarioDto> listarUsuarios() {
        List<UsuarioDto> listaUsuarioDto = new ArrayList<>();
        String sql = "SELECT username, correo, nom_completo, run FROM usuarios";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UsuarioDto usuario = new UsuarioDto(
                        rs.getString("username"),
                        rs.getString("correo"),
                        rs.getString("nom_completo"),
                        rs.getString("run"));
                listaUsuarioDto.add(usuario);
            }
        } catch (Exception e) {
            // Puedes lanzar la excepción o manejarla según tu requerimiento
            throw new RuntimeException("Error al listar usuarios", e);
        }
        return listaUsuarioDto;
    }

    public void guardarUsuario(UsuarioDto usuarioDto) {
        String sql = "INSERT INTO usuarios (username, correo, nom_completo, run) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuarioDto.getUsername());
            pstmt.setString(2, usuarioDto.getCorreo());
            pstmt.setString(3, usuarioDto.getNomCompleto());
            pstmt.setString(4, usuarioDto.getRun());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar usuario", e);
        }
    }

    public boolean existeUsuarioPorUsername(String username) {
        String sql = "SELECT 1 FROM usuarios WHERE username = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al validar existencia del usuario", e);
        }
    }

    public UsuarioGraphQlDto obtenerUsuarioPorUsername(String username) {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new UsuarioGraphQlDto(
                            rs.getString("username"),
                            rs.getString("correo"),
                            rs.getString("nom_completo"),
                            rs.getString("run")
                    );
                } else {
                    return null;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al al buscar usuario", e);
        }
    }

}
