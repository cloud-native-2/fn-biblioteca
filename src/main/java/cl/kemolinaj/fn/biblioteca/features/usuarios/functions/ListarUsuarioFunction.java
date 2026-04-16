package cl.kemolinaj.fn.biblioteca.features.usuarios.functions;

import cl.kemolinaj.fn.biblioteca.features.usuarios.dtos.UsuarioDto;
import cl.kemolinaj.fn.biblioteca.features.usuarios.service.UsuarioService;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import java.util.List;
import java.util.Optional;

public class ListarUsuarioFunction {
//    private final UsuarioService usuarioService = new UsuarioService();
//
//    @FunctionName("ListarUsuario")
//    public HttpResponseMessage listarUsuario(
//            @HttpTrigger(
//                    name = "req",
//                    methods = {HttpMethod.GET},
//                    authLevel = AuthorizationLevel.ANONYMOUS)
//            final HttpRequestMessage<Optional<String>> request,
//            final ExecutionContext context){
//        context.getLogger().info("[Function: CrearUsuario] init");
//        try {
//            List<UsuarioDto> listaUsuarios = usuarioService.listarUsuarios();
//            // Convertir la lista a JSON
//            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//            String json = ow.writeValueAsString(listaUsuarios);
//            return request.createResponseBuilder(HttpStatus.OK)
//                    .header("Content-Type", "application/json")
//                    .body(json)
//                    .build();
//        } catch (Exception e) {
//            context.getLogger().severe(e.getMessage());
//            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error al listar usuarios: " + e.getMessage())
//                    .build();
//        }
//
//    }
}
