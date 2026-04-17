package cl.kemolinaj.fn.biblioteca.features.libros.functions;

import cl.kemolinaj.fn.biblioteca.features.libros.GraphQlInput.GraphQlMutations;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import graphql.ExecutionInput;
import graphql.GraphQL;

import java.util.Map;
import java.util.Optional;

public class GraphQlFunction {
    @FunctionName("graphqlLibros")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "graphqlLibros"
            ) final HttpRequestMessage<Optional<Map<String, Object>>> request,
            final ExecutionContext context) {

        try {
            Optional<Map<String, Object>> bodyOpt = request.getBody();

            if (bodyOpt.isEmpty() || bodyOpt.get().get("query") == null) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json")
                        .body(Map.of("error", "Debes enviar un body con la propiedad 'query'"))
                        .build();
            }

            Map<String, Object> body = bodyOpt.get();
            String query = body.get("query").toString();

            GraphQL graphQL = GraphQlMutations.build();

            ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                    .query(query)
                    .build();

           graphQL.execute(executionInput);

            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body("OK")
                    .build();

        } catch (Exception e) {
            context.getLogger().severe("Error ejecutando GraphQL: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(Map.of(
                            "error", "Error interno del servidor",
                            "detail", e.getMessage()
                    ))
                    .build();
        }
    }
}
