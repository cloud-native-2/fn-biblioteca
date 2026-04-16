package cl.kemolinaj.fn.biblioteca.features.prestamos.functions;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroRqDto;
import cl.kemolinaj.fn.biblioteca.features.prestamos.dtos.PrestamoRsDto;
import cl.kemolinaj.fn.biblioteca.features.prestamos.dtos.PretamoRqDto;
import cl.kemolinaj.fn.biblioteca.features.prestamos.service.PrestamoService;
import cl.kemolinaj.fn.biblioteca.shared.utils.ObjectUtil;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.List;
import java.util.Optional;

public class PrestamosRest {
    private final PrestamoService prestamoService = new PrestamoService();

    @FunctionName("prestamos")
    public HttpResponseMessage restFunction(
            @HttpTrigger(name = "req",
                    methods = {HttpMethod.POST, HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE},
                    authLevel = AuthorizationLevel.FUNCTION)
            final HttpRequestMessage<Optional<PretamoRqDto>> request,
            final ExecutionContext context
    ) {
        context.getLogger().info("[Function: prestamos] init");
        try {
            return switch (request.getHttpMethod()){
                case POST -> {
                    if (request.getBody().isEmpty()) {
                        yield request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                                .body("Falta el cuerpo de la solicitud.")
                                .build();
                    }
                    yield request.createResponseBuilder(HttpStatus.CREATED)
                            .header("Content-Type", "application/json")
                            .body(crearPrestamo(context, request))
                            .build();
                }
                case GET -> {
                    yield request.createResponseBuilder(HttpStatus.OK)
                            .header("Content-Type", "application/json")
                            .body(obtenerPrestamos(context))
                            .build();
                }
                default -> request.createResponseBuilder(HttpStatus.METHOD_NOT_ALLOWED)
                        .body("Método no permitido")
                        .build();
            } ;
        } catch (Exception e) {
            context.getLogger().severe("[Function: prestamos] Error processing request: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor")
                    .build();
        }
    }

    private String obtenerPrestamos(final ExecutionContext context) {
        context.getLogger().info("[Function: obtenerPrestamos] init");
        List<PrestamoRsDto> listaPrestamos = prestamoService.listarPrestamos();
        context.getLogger().info("[Function: obtenerPrestamos] end");
        return ObjectUtil.objectToString(listaPrestamos);
    }

    private String crearPrestamo(final ExecutionContext context, final HttpRequestMessage<Optional<PretamoRqDto>> request) {
        context.getLogger().info("[Function: crearPrestamo] init");
        PretamoRqDto prestamoRqDto = request.getBody().get();
        prestamoService.insertarPrestamo(prestamoRqDto);
        context.getLogger().info("[Function: crearPrestamo] end");
        return "Prestamo creado exitosamente";
    }
}
