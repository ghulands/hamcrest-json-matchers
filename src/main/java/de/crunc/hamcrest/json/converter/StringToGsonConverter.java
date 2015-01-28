package de.crunc.hamcrest.json.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Converts a JSON string to a {@link JsonElement}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.1
 */
public class StringToGsonConverter implements ToGsonConverter<String> {

    private final JsonParser parser = new JsonParser();

    @Override
    public Class<String> sourceType() {
        return String.class;
    }

    @Override
    public JsonElement toJsonElement(String source) {
        return parser.parse(source);
    }
}
