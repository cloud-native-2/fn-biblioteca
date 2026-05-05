package cl.kemolinaj.fn.biblioteca.features.events.services;

import cl.kemolinaj.fn.biblioteca.features.events.dtos.CorreoRqDto;
import cl.kemolinaj.fn.biblioteca.shared.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Logger;

public class EventService {
    public void enviarCorreo(CorreoRqDto correoRqDto, final Logger logger) {
        String sql = "INSERT INTO CORREOS (CORREO_DESTINO, ASUNTO, MENSAJE, FECHA_HORA) VALUES (?, ?, ?, ?)";
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Santiago"));

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, correoRqDto.correoDestino());
            pstmt.setString(2, correoRqDto.asunto());
            pstmt.setString(3, correoRqDto.mensaje());
            pstmt.setTimestamp(4, Timestamp.valueOf(now));

            logger.info("[INSERT-CORREO] - Insertando correo");
            pstmt.executeUpdate();
            logger.info("[INSERT-CORREO] - Correo insertado correctamente");

        } catch (Exception e) {
            logger.info("[INSERT-CORREO] - Error al insertar correo");
            logger.severe(e.getMessage());
            throw new RuntimeException("Error al insertar correo ", e);
        }
    }
}
