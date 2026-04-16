package cl.kemolinaj.fn.biblioteca.shared.utils;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

public class ObjectUtil {
    public static final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public static String objectToString(Object object) {
        return ow.writeValueAsString(object);
    }
}
