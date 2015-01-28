package de.crunc.hamcrest.json;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonElement;
import org.vertx.java.core.json.JsonObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Fluent builder for {@link JsonArray}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class VertxJsonArrayBuilder {

    private final List<Object> values;

    private VertxJsonArrayBuilder() {
        values = new ArrayList<>();
    }

    public VertxJsonArrayBuilder add(@Nullable JsonElement element) {
        if (element != null) {
            values.add(element);
            return this;
        } else {
            return addNull();
        }
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonArrayBuilder add(@Nullable VertxJsonObjectBuilder builder) {
        if (builder != null) {
            return add(builder.build());
        }
        return add((JsonObject) null);
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonArrayBuilder add(@Nullable VertxJsonArrayBuilder builder) {
        if (builder != null) {
            return add(builder.build());
        }
        return add((JsonArray) null);
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonArrayBuilder add(@Nullable String value) {
        if (value != null) {
            values.add(value);
            return this;
        } else {
            return addNull();
        }
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonArrayBuilder add(@Nullable Number value) {
        if (value != null) {
            values.add(value);
            return this;
        } else {
            return addNull();
        }
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonArrayBuilder add(@Nullable Boolean value) {
        if (value != null) {
            values.add(value);
            return this;
        } else {
            return addNull();
        }
    }

    /**
     * @since 2.2.2
     */
    public VertxJsonArrayBuilder addNull() {
        values.add(null);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonArray build() {
        JsonArray array = new JsonArray();

        for (Object value: values) {
            array.add(value);
        }

        return array;
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
    public static VertxJsonArrayBuilder array() {
        return new VertxJsonArrayBuilder();
    }
}
