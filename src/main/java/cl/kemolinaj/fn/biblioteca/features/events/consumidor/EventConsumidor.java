package cl.kemolinaj.fn.biblioteca.features.events.consumidor;

import cl.kemolinaj.fn.biblioteca.features.events.dtos.CorreoRqDto;
import cl.kemolinaj.fn.biblioteca.features.events.services.EventService;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class EventConsumidor {
    private final EventService service = new EventService();
    Gson gson = new Gson();

    @FunctionName("EnvioCorreo")
    public void envioCorreo(
            @EventGridTrigger(name = "EnvioCorreoEvent") String content,
            final ExecutionContext context
    ) {
        Logger logger = context.getLogger();
        logger.info("[EnvioCorreoEvent] init");

        try {
            JsonObject eventGridEvent = gson.fromJson(content, JsonObject.class);

            String data = eventGridEvent.get("data").toString();
            CorreoRqDto correoRqDto = gson.fromJson(data, CorreoRqDto.class);
            logger.info("[EnvioCorreoEvent] Se procede a enviar correo");
            service.enviarCorreo(correoRqDto, logger);
            logger.info("[EnvioCorreoEvent] Correo enviado exitosamente");

        } catch (Exception e) {
            // Captura y loguea cualquier excepción
            logger.severe("Error al procesar el evento: " + e.getMessage());
            e.printStackTrace(); // Imprime el stacktrace en consola
        } finally {
            logger.info("[EnvioCorreoEvent] end");
        }
    }

    @FunctionName("GenerarComprobante")
    public void generarComprobante(
            @EventGridTrigger(name = "GenerarComprobanteEvent") String content,
            final ExecutionContext context
    ) {
        Logger logger = context.getLogger();
        logger.info("[GenerarComprobanteEvent] init");
        JsonObject eventGridEvent = gson.fromJson(content, JsonObject.class);

        logger.info("[GenerarComprobanteEvent] Procesando evento de generación de comprobante");

        logger.info("[GenerarComprobanteEvent] En construcción, implementación pendiente");

        logger.info("[GenerarComprobanteEvent] end");
    }

}
