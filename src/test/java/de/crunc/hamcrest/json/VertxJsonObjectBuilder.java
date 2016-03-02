package de.crunc.hamcrest.json;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Fluent builder for {@link JsonObject}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 1.0
 */
public class VertxJsonObjectBuilder {

    private final Map<String, Object> values;

    private VertxJsonObjectBuilder() {
        values = new HashMap<>();
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonObjectBuilder put(String field, @Nullable Object element) {
        if (element != null) {
            values.put(field, element);
            return this;
        } else {
            return putNull(field);
        }
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonObjectBuilder put(String field, @Nullable VertxJsonObjectBuilder objectBuilder) {
        if (objectBuilder != null) {
            return put(field, objectBuilder.build());
        }
        return put(field, (JsonObject) null);
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonObjectBuilder put(String field, @Nullable VertxJsonArrayBuilder arrayBuilder) {
        if (arrayBuilder != null) {
            return put(field, arrayBuilder.build());
        }
        return put(field, (JsonArray) null);
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonObjectBuilder put(String field, @Nullable String value) {
        if (value != null) {
            values.put(field, value);
            return this;
        } else {
            return putNull(field);
        }
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonObjectBuilder put(String field, @Nullable Number value) {
        if (value != null) {
            values.put(field, value);
            return this;
        } else {
            return putNull(field);
        }
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonObjectBuilder put(String field, @Nullable Boolean value) {
        if (value != null) {
            values.put(field, value);
            return this;
        } else {
            return putNull(field);
        }
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonObjectBuilder putNull(String field) {
        values.put(field, null);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonObject build() {
        JsonObject object = new JsonObject();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            object.put(entry.getKey(), entry.getValue());
        }

        return object;
    }

    /**
     * @since 2.2.2
     */
    public String encode() {
        return build().encode();
    }

    /**
     * @since 2.2.2
     */
    public static VertxJsonObjectBuilder object() {
        return new VertxJsonObjectBuilder();
    }
}
