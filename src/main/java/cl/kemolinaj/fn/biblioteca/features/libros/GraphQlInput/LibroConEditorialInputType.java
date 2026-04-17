package cl.kemolinaj.fn.biblioteca.features.libros.GraphQlInput;

import graphql.Scalars;
import graphql.schema.GraphQLInputObjectType;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LibroConEditorialInputType {
    public static GraphQLInputObjectType build() {
        return GraphQLInputObjectType.newInputObject()
                .name("LibroConEditorialInput")
                .field(field -> field.name("libroNombre").type(Scalars.GraphQLString))
                .field(field -> field.name("editorialNombre").type(Scalars.GraphQLString))
                .build();
    }
}
