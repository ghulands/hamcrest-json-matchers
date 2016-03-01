package de.crunc.hamcrest.json;

import com.google.gson.*;

import javax.annotation.Nullable;

/**
 * Fluent builder for {@link JsonObject}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.2.2
 */
public class JsonObjectBuilder {

    private final JsonObject values;

    private JsonObjectBuilder() {
        values = new JsonObject();
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable JsonElement element) {
        if (element != null) {
            values.add(field, element);
            return this;
        } else {
            return putNull(field);
        }
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable JsonObjectBuilder objectBuilder) {
        if (objectBuilder != null) {
            return put(field, objectBuilder.build());
        }
        return put(field, (JsonObject) null);
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable JsonArrayBuilder arrayBuilder) {
        if (arrayBuilder != null) {
            return put(field, arrayBuilder.build());
        }
        return put(field, (JsonArray) null);
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable String value) {
        if (value != null) {
            return put(field, new JsonPrimitive(value));
        }
        else {
            return putNull(field);
        }
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable Number value) {
        if (value != null) {
            return put(field, new JsonPrimitive(value));
        }
        else {
            return putNull(field);
        }
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable Boolean value) {
        if (value != null) {
            return put(field, new JsonPrimitive(value));
        }
        else {
            return putNull(field);
        }
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder putNull(String field) {
        values.add(field, com.google.gson.JsonNull.INSTANCE);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonObject build() {
        return values;
    }

    /**
     * @since 2.2.2
     */
    public String encode() {
        return build().toString();
    }

    /**
     * @since 2.2.2
     */
    public static JsonObjectBuilder object() {
        return new JsonObjectBuilder();
    }
}
