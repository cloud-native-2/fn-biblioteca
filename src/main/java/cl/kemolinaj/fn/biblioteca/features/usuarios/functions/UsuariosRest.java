package cl.kemolinaj.fn.biblioteca.features.usuarios.functions;

import cl.kemolinaj.fn.biblioteca.features.usuarios.dtos.UsuarioDto;
import cl.kemolinaj.fn.biblioteca.features.usuarios.service.UsuarioService;
import cl.kemolinaj.fn.biblioteca.shared.utils.ObjectUtil;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.List;
import java.util.Optional;

public class UsuariosRest {
    private final UsuarioService usuarioService = new UsuarioService();

    @FunctionName("usuarios")
    public HttpResponseMessage restFunction(
            @HttpTrigger(name = "req",
                    methods = {HttpMethod.POST, HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            final HttpRequestMessage<Optional<UsuarioDto>> request,
            final ExecutionContext context
    ) {
        context.getLogger().info("[Function: usuarios] init");
        try {
            return switch (request.getHttpMethod()) {
                case POST -> {
                    if (request.getBody().isEmpty()) {
                        yield request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                                .body("Falta el cuerpo de la solicitud.")
                                .build();
                    }
                    yield request.createResponseBuilder(HttpStatus.CREATED)
                            .header("Content-Type", "application/json")
                            .body(crearUsuario(context, request))
                            .build();
                }
                case GET -> request.createResponseBuilder(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(obtenerUsuarios(context))
                        .build();
                default -> request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error API usuarios")
                        .build();
            };
        } catch (Exception e){
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("Error API usuarios")
                    .build();
        }
    }

    private String obtenerUsuarios(final ExecutionContext context) {
        context.getLogger().info("[Function: obtnerUsuarios] init");
        List<UsuarioDto> listaLibros = usuarioService.listarUsuarios();
        context.getLogger().info("[Function: obtnerUsuarios] end");
        return ObjectUtil.objectToString(listaLibros);
    }

    private String crearUsuario(final ExecutionContext context, final HttpRequestMessage<Optional<UsuarioDto>> request) {
        context.getLogger().info("[Function: crearLibro] init");
        UsuarioDto usuarioRq = request.getBody().get();
        usuarioService.guardarUsuario(usuarioRq);
        context.getLogger().info("[Function: crearLibro] end");
        return "Libro creado exitosamente";
    }
}
