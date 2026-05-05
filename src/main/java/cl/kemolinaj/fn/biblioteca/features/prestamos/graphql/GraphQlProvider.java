package cl.kemolinaj.fn.biblioteca.features.prestamos.graphql;

import cl.kemolinaj.fn.biblioteca.features.prestamos.dtos.PrestamoGraphQlDto;
import cl.kemolinaj.fn.biblioteca.features.prestamos.service.GraphQlPrestamoService;
import cl.kemolinaj.fn.biblioteca.features.usuarios.dtos.UsuarioGraphQlDto;
import cl.kemolinaj.fn.biblioteca.features.usuarios.service.UsuarioService;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import static graphql.schema.GraphQLList.list;

@NoArgsConstructor
public class GraphQlProvider {
    private static volatile GraphQL graphQL;
    private static final GraphQlPrestamoService prestamoService = new GraphQlPrestamoService();
    private static final UsuarioService usuarioService = new UsuarioService();

    public static GraphQL build() {
        if (graphQL == null) {
            synchronized (GraphQlProvider.class) {
                if (graphQL == null) {

                    GraphQLObjectType editorialType = GraphQLObjectType.newObject()
                            .name("Editorial")
                            .field(field -> field.name("nombre").type(Scalars.GraphQLString))
                            .build();

                    GraphQLObjectType libroType = GraphQLObjectType.newObject()
                            .name("Libro")
                            .field(field -> field.name("id").type(Scalars.GraphQLID))
                            .field(field -> field.name("nombre").type(Scalars.GraphQLString))
                            .field(field -> field.name("editorial").type(editorialType))
                            .build();

                    GraphQLObjectType usuarioType = GraphQLObjectType.newObject()
                            .name("Usuario")
                            .field(field -> field.name("username").type(Scalars.GraphQLString))
                            .field(field -> field.name("nombre").type(Scalars.GraphQLString))
                            .field(field -> field.name("email").type(Scalars.GraphQLString))
                            .field(field -> field.name("run").type(Scalars.GraphQLString))
                            .build();

                    GraphQLObjectType prestamoType = GraphQLObjectType.newObject()
                            .name("Prestamo")
                            .field(field -> field.name("folio").type(Scalars.GraphQLID))
                            .field(field -> field.name("fechaEntrega").type(Scalars.GraphQLString))
                            .field(field -> field.name("fechaDevolucion").type(Scalars.GraphQLString))
                            .field(field -> field.name("usuario").type(usuarioType))
                            .field(field -> field.name("libro").type(libroType))
                            .build();

                    GraphQLInputObjectType filtroUsuarioType = GraphQLInputObjectType.newInputObject()
                            .name("FiltroUsuarioInput")
                            .field(field -> field.name("username").type(Scalars.GraphQLString))
                            .field(field -> field.name("nombre").type(Scalars.GraphQLString))
                            .field(field -> field.name("email").type(Scalars.GraphQLString))
                            .field(field -> field.name("run").type(Scalars.GraphQLString))
                            .build();

                    DataFetcher<UsuarioGraphQlDto> usuarioFetcher = environment -> {
                        String username = environment.getArgument("username");
                        return usuarioService.obtenerUsuarioPorUsername(username);
                    };

                    DataFetcher<List<PrestamoGraphQlDto>> prestamosPorUsuarioFetcher = environment -> {
                        Map<String, Object> filtro = environment.getArgument("filtro");

                        String username = filtro != null ? (String) filtro.get("username") : null;
                        String nombre = filtro != null ? (String) filtro.get("nombre") : null;
                        String email = filtro != null ? (String) filtro.get("email") : null;
                        String run = filtro != null ? (String) filtro.get("run") : null;

                        return prestamoService.listarPrestamosConDetalle(username, nombre, email, run);
                    };

                    GraphQLObjectType queryType = GraphQLObjectType.newObject()
                            .name("Query")
                            .field(field -> field
                                    .name("usuario")
                                    .type(usuarioType)
                                    .argument(arg -> arg.name("username").type(Scalars.GraphQLString))
                                    .dataFetcher(usuarioFetcher)
                            )
                            .field(field -> field
                                    .name("prestamosPorUsuario")
                                    .type(list(prestamoType))
                                    .argument(arg -> arg.name("filtro").type(filtroUsuarioType))
                                    .dataFetcher(prestamosPorUsuarioFetcher)
                            )
                            .build();

                    GraphQLSchema schema = GraphQLSchema.newSchema()
                            .query(queryType)
                            .build();

                    graphQL = GraphQL.newGraphQL(schema).build();
                }
            }
        }
        return graphQL;
    }
}
