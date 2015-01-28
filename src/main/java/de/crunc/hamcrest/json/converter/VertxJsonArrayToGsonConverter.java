package de.crunc.hamcrest.json.converter;


import com.google.gson.*;

import javax.annotation.Nullable;

/**
 * Converts {@link org.vertx.java.core.json.JsonObject} to {@link JsonElement}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.1
 */
public class VertxJsonArrayToGsonConverter implements ToGsonConverter<org.vertx.java.core.json.JsonArray> {

    static final VertxJsonArrayToGsonConverter INSTANCE = new VertxJsonArrayToGsonConverter();

    @Override
    public Class<org.vertx.java.core.json.JsonArray> sourceType() {
        return org.vertx.java.core.json.JsonArray.class;
    }

    @Override
    public JsonElement toJsonElement(@Nullable org.vertx.java.core.json.JsonArray source) {

        if (source == null) {
            return JsonNull.INSTANCE;
        }

        JsonArray gsonArray = new JsonArray();

        for (int index = 0; index < source.size(); ++index) {
            Object value = source.get(index);
            final JsonElement element;

            if (value == null) {
                element = JsonNull.INSTANCE;
            } else if (value instanceof org.vertx.java.core.json.JsonObject) {
                element = VertxJsonObjectToGsonConverter.INSTANCE.toJsonElement((org.vertx.java.core.json.JsonObject) value);
            } else if (value instanceof org.vertx.java.core.json.JsonArray) {
                element = VertxJsonArrayToGsonConverter.INSTANCE.toJsonElement((org.vertx.java.core.json.JsonArray) value);
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
