package cl.kemolinaj.fn.biblioteca.features.libros.functions;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroRqDto;
import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroRsDto;
import cl.kemolinaj.fn.biblioteca.features.libros.service.LibroService;
import cl.kemolinaj.fn.biblioteca.shared.utils.ObjectUtil;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import java.util.List;
import java.util.Optional;


public class LibrosRest {
    private final LibroService libroService = new LibroService();

    @FunctionName("libros")
    public HttpResponseMessage restFunction(
            @HttpTrigger(name = "req",
                    methods = {HttpMethod.POST, HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE},
                    authLevel = AuthorizationLevel.FUNCTION)
            final HttpRequestMessage<Optional<LibroRqDto>> request,
            final ExecutionContext context
    ) {
        context.getLogger().info("[Function: libros] init");
        try{
            return switch (request.getHttpMethod()) {
                case POST -> {
                    if (request.getBody().isEmpty()) {
                        yield request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                                .body("Falta el cuerpo de la solicitud.")
                                .build();
                    }
                    yield request.createResponseBuilder(HttpStatus.CREATED)
                            .header("Content-Type", "application/json")
                            .body(crearLibro(context, request))
                            .build();
                }
                case GET -> request.createResponseBuilder(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(obtenerLibros(context))
                        .build();
                default -> request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error API libros")
                        .build();
            };
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error API libros")
                    .build();
        }
    }

    private String obtenerLibros(final ExecutionContext context) {
        context.getLogger().info("[Function: obtenerLibros] init");
        List<LibroRsDto> listaLibros = libroService.listarLibros();
        context.getLogger().info("[Function: obtenerLibros] end");
        return ObjectUtil.objectToString(listaLibros);
    }

    private String crearLibro(final ExecutionContext context, final HttpRequestMessage<Optional<LibroRqDto>> request) {
        context.getLogger().info("[Function: crearLibro] init");
        LibroRqDto libroRqDto = request.getBody().get();
        libroService.insertarLibro(libroRqDto);
        context.getLogger().info("[Function: crearLibro] end");
        return "Libro creado exitosamente";
    }
}
