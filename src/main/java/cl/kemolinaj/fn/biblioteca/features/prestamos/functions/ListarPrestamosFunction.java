package cl.kemolinaj.fn.biblioteca.features.prestamos.functions;

import cl.kemolinaj.fn.biblioteca.features.prestamos.dtos.PrestamoRsDto;
import cl.kemolinaj.fn.biblioteca.features.prestamos.service.PrestamoService;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import java.util.List;
import java.util.Optional;

public class ListarPrestamosFunction {
//    private final PrestamoService prestamoService = new PrestamoService();
//
//    @FunctionName("ListarPrestamos")
//    public HttpResponseMessage listarPrestamos(
//            @HttpTrigger(name = "req",
//                    methods = {HttpMethod.GET},
//                    authLevel = AuthorizationLevel.ANONYMOUS)
//            final HttpRequestMessage<Optional<String>> request,
//            final ExecutionContext context) {
//        context.getLogger().info("[Function: ListarPrestamos] init");
//        try {
//            List<PrestamoRsDto> listaPrestamos = prestamoService.listarPrestamos();
//            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//            String json = ow.writeValueAsString(listaPrestamos);
//            return request.createResponseBuilder(HttpStatus.OK)
//                    .header("Content-Type", "application/json")
//                    .body(json)
//                    .build();
//        } catch (Exception e) {
//            context.getLogger().severe(e.getMessage());
//            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error al listar préstamos: " + e.getMessage())
//                    .build();
//        }
//    }

}
