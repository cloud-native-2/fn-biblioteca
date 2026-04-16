package cl.kemolinaj.fn.biblioteca.features.libros.functions;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroRqDto;
import cl.kemolinaj.fn.biblioteca.features.libros.service.LibroService;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

public class CrearLibroFunction {
//    private final LibroService libroService = new LibroService();
//
//    @FunctionName("CrearLibro")
//    public HttpResponseMessage crearLibro(
//            @HttpTrigger(name = "req",
//                    methods = {HttpMethod.POST},
//                    authLevel = AuthorizationLevel.ANONYMOUS)
//            final HttpRequestMessage<Optional<LibroRqDto>> request,
//            final ExecutionContext context) {
//        context.getLogger().info("[Function: CrearLibro] init");
//        try {
//            if (request.getBody().isEmpty()) {
//                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
//                        .body("Falta el cuerpo de la solicitud.")
//                        .build();
//            }
//            libroService.insertarLibro(request.getBody().get());
//
//            return request.createResponseBuilder(HttpStatus.CREATED)
//                    .body("Libro guardado con éxito")
//                    .build();
//        } catch (Exception e) {
//            context.getLogger().severe(e.getMessage());
//            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error al guardar el libro: " + e.getMessage())
//                    .build();
//        }
//    }

}
