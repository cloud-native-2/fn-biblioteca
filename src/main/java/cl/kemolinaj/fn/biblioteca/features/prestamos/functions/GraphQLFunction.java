package cl.kemolinaj.fn.biblioteca.features.prestamos.functions;

import cl.kemolinaj.fn.biblioteca.features.prestamos.graphql.GraphQlProvider;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import graphql.ExecutionInput;
import graphql.GraphQL;

import java.util.Map;
import java.util.Optional;

public class GraphQLFunction {
    @FunctionName("graphqlPretamos")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "graphqlPretamos"
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

            String query = bodyOpt.get().get("query").toString();

            GraphQL graphQL = GraphQlProvider.build();

            ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                    .query(query)
                    .build();

            Map<String, Object> result = graphQL.execute(executionInput).toSpecification();

            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(result)
                    .build();

        } catch (Exception e) {
            context.getLogger().severe("Error ejecutando GraphQL: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(Map.of("error", "Error interno del servidor", "detail", e.getMessage()))
                    .build();
        }
    }
}
