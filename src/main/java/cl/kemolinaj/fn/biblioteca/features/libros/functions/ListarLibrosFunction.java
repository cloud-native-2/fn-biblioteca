package cl.kemolinaj.fn.biblioteca.features.libros.functions;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroRsDto;
import cl.kemolinaj.fn.biblioteca.features.libros.service.LibroService;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import java.util.List;
import java.util.Optional;

public class ListarLibrosFunction {
//    private final LibroService libroService = new LibroService();
//
//    @FunctionName("ListarLibros")
//    public HttpResponseMessage listarLibros(
//            @HttpTrigger(name = "req",
//                    methods = {HttpMethod.GET},
//                    authLevel = AuthorizationLevel.ANONYMOUS)
//            final HttpRequestMessage<Optional<String>> request,
//            final ExecutionContext context) {
//        context.getLogger().info("[Function: ListarLibros] init");
//        try {
//            List<LibroRsDto> listaLibros = libroService.listarLibros();
//            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//            String json = ow.writeValueAsString(listaLibros);
//            return request.createResponseBuilder(HttpStatus.OK)
//                    .header("Content-Type", "application/json")
//                    .body(json)
//                    .build();
//        } catch (Exception e) {
//            context.getLogger().severe(e.getMessage());
//            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error al listar libros: " + e.getMessage())
//                    .build();
//        }
//    }

}
