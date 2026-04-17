package cl.kemolinaj.fn.biblioteca.features.libros.GraphQlInput;

import cl.kemolinaj.fn.biblioteca.features.libros.dtos.LibroGraphQlDto;
import cl.kemolinaj.fn.biblioteca.features.libros.service.LibroService;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.hibernate.graph.Graph;

import java.util.Map;

public class GraphQlMutations {
    private static final LibroService libroService = new LibroService();

    public static GraphQL build(){

        GraphQLObjectType editorialType = GraphQLObjectType.newObject()
                .name("Editorial")
                .field(field -> field.name("nombre").type(Scalars.GraphQLString))
                .build();

        GraphQLObjectType libroType = GraphQLObjectType.newObject()
                .name("Libro")
                .field(field -> field.name("nombre").type(Scalars.GraphQLString))
                .field(field -> field.name("editorial").type(editorialType))
                .build();

        DataFetcher<LibroGraphQlDto> crearLibroConEditorialFetcher = environment -> {
            Map<String, Object> input = environment.getArgument("input");

            String libroNombre = (String) input.get("libroNombre");
            String editorialNombre = (String) input.get("editorialNombre");

            return libroService.crearLibroConEditorial(libroNombre, editorialNombre);
        };

        GraphQLObjectType mutationType = GraphQLObjectType.newObject()
                .name("Mutation")
                .field(field -> field
                        .name("crearLibroConEditorial")
                        .type(libroType)
                        .dataFetcher(crearLibroConEditorialFetcher)
                )
                .build();

        GraphQLObjectType queryType = GraphQLObjectType.newObject()
                .name("Query")
                .field(field -> field
                        .name("health")
                        .type(Scalars.GraphQLString)
                        .dataFetcher(env -> "OK"))
                .build();

        GraphQLSchema schema = GraphQLSchema.newSchema()
                .mutation(mutationType)
                .query(queryType)
                .build();

        return GraphQL.newGraphQL(schema).build();
    }
}
