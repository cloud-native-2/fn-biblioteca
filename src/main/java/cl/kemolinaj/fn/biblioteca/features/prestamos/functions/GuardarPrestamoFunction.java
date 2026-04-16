package cl.kemolinaj.fn.biblioteca.features.prestamos.functions;

import cl.kemolinaj.fn.biblioteca.features.prestamos.dtos.PretamoRqDto;
import cl.kemolinaj.fn.biblioteca.features.prestamos.service.PrestamoService;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

public class GuardarPrestamoFunction {
//    private final PrestamoService prestamoService = new PrestamoService();
//
//    @FunctionName("CrearPrestamo")
//    public HttpResponseMessage crearPrestamo(
//            @HttpTrigger(
//                    name = "req",
//                    methods = {HttpMethod.POST},
//                    authLevel = AuthorizationLevel.ANONYMOUS)
//            final HttpRequestMessage<Optional<PretamoRqDto>> request,
//            final ExecutionContext context
//    ) {
//        context.getLogger().info("[Function: CrearPrestamo] init");
//        try {
//            if (request.getBody().isEmpty()) {
//                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
//                        .body("Falta el cuerpo de la solicitud.")
//                        .build();
//            }
//            prestamoService.insertarPrestamo(request.getBody().get());
//            return request.createResponseBuilder(HttpStatus.CREATED)
//                    .body("Préstamo registrado con éxito.")
//                    .build();
//        } catch (IllegalArgumentException e) {
//            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
//                    .body(e.getMessage())
//                    .build();
//        } catch (Exception e) {
//            context.getLogger().severe("Error al crear préstamo: " + e.getMessage());
//            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error al registrar el préstamo: " + e.getMessage())
//                    .build();
//        }
//    }

}
