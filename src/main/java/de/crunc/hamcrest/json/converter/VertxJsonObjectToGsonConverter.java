package de.crunc.hamcrest.json.converter;


import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Converts {@link io.vertx.core.json.JsonObject} to {@link JsonElement}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.1
 */
public class VertxJsonObjectToGsonConverter implements ToGsonConverter<io.vertx.core.json.JsonObject> {

    /**
     * @since 0.1
     */
    static final VertxJsonObjectToGsonConverter INSTANCE = new VertxJsonObjectToGsonConverter();

    @Override
    public Class<io.vertx.core.json.JsonObject> sourceType() {
        return io.vertx.core.json.JsonObject.class;
    }

    @Override
    public JsonElement toJsonElement(io.vertx.core.json.JsonObject source) {

        JsonObject gsonObject = new JsonObject();

        for (String name : source.fieldNames()) {
            Object value = source.getValue(name);
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

            gsonObject.add(name, element);
        }

        return gsonObject;
    }
}
