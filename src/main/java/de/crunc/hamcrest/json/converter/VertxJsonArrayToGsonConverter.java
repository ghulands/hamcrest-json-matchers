package de.crunc.hamcrest.json.converter;


import com.google.gson.*;

/**
 * Converts {@link io.vertx.core.json.JsonObject} to {@link JsonElement}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.1
 */
public class VertxJsonArrayToGsonConverter implements ToGsonConverter<io.vertx.core.json.JsonArray> {

    static final VertxJsonArrayToGsonConverter INSTANCE = new VertxJsonArrayToGsonConverter();

    @Override
    public Class<io.vertx.core.json.JsonArray> sourceType() {
        return io.vertx.core.json.JsonArray.class;
    }

    @Override
    public JsonElement toJsonElement(io.vertx.core.json.JsonArray source) {

        JsonArray gsonArray = new JsonArray();

        for (int index = 0; index < source.size(); ++index) {
            Object value = source.getValue(index);
            final JsonElement element;

            if (value == null) {
                element = JsonNull.INSTANCE;
            } else if (value instanceof io.vertx.core.json.JsonObject) {
                element = VertxJsonObjectToGsonConverter.INSTANCE.toJsonElement((io.vertx.core.json.JsonObject) value);
            } else if (value instanceof io.vertx.core.json.JsonArray) {
                element = VertxJsonArrayToGsonConverter.INSTANCE.toJsonElement((io.vertx.core.json.JsonArray) value);
            } else if (value instanceof Boolean) {
                element = new JsonPrimitive((Boolean) value);
            } else if (value instanceof Number) {
                element = new JsonPrimitive((Number) value);
            } else if (value instanceof String) {
                element = new JsonPrimitive((String) value);
            } else {
                throw new GsonConversionException("can not convert <" + value + "> to a <JsonElement>");
            }

            gsonArray.add(element);
        }

        return gsonArray;
    }
}
