package de.crunc.hamcrest.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.annotation.Nullable;

/**
 * Fluent builder for {@link JsonArray}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.2.2
 */
public class JsonArrayBuilder {

    private final JsonArray array;

    private JsonArrayBuilder() {
        array = new JsonArray();
    }

    public JsonArrayBuilder add(@Nullable JsonElement element) {
        if (element != null) {
            array.add(element);
            return this;
        } else {
            return addNull();
        }
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable JsonObjectBuilder builder) {
        if (builder != null) {
            return add(builder.build());
        }
        return add((JsonObject) null);
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable JsonArrayBuilder builder) {
        if (builder != null) {
            return add(builder.build());
        }
        return add((JsonArray) null);
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable String value) {
        if (value != null) {
            return add(new JsonPrimitive(value));
        } else {
            return addNull();
        }
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable Number value) {
        if (value != null) {
            return add(new JsonPrimitive(value));
        } else {
            return addNull();
        }
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable Boolean value) {
        if (value != null) {
            return add(new JsonPrimitive(value));
        } else {
            return addNull();
        }
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder addNull() {
        array.add(com.google.gson.JsonNull.INSTANCE);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonArray build() {
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
    public static JsonArrayBuilder array() {
        return new JsonArrayBuilder();
    }
}
