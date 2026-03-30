package cl.kemolinaj.fn.biblioteca.features.usuarios.functions;

import cl.kemolinaj.fn.biblioteca.features.usuarios.dtos.UsuarioRqDto;
import cl.kemolinaj.fn.biblioteca.shared.config.DatabaseConfig;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CrearUsuarioFunction {


    @FunctionName("CrearUsuario")
    public HttpResponseMessage crearUsuario(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<UsuarioRqDto> request,
            final ExecutionContext context) throws Exception {
        context.getLogger().info("[Function: CrearUsuario] init");
        UsuarioRqDto body = request.getBody();
        if (body == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).build();
        }

        Connection conn = DatabaseConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT 1 FROM DUAL");
        int value = 0;
        if (rs.next()) {
            value = rs.getInt(1);
        }

        stmt.close();
        conn.close();

        return request.createResponseBuilder(HttpStatus.OK)
                .body("Conexión OK. Resultado: " + value)
                .build();
    }
}
