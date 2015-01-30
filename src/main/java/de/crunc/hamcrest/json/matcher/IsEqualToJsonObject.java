package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Matches {@link JsonObject} if it is equal to the given array.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.2
 */
public class IsEqualToJsonObject<T> extends IsJsonObject<T> {

    /**
     * Creates a new matcher that checks for equality to an empty object.
     *
     * @since 0.2
     */
    public IsEqualToJsonObject() {
        this(null);
    }

    /**
     * Creates a new matcher that checks for equality to the expected object. If the expected object is {@code null}
     * then this matcher checks for equality to an empty object.
     *
     * @param expectedObject The expected object. Can be {@code null}.
     * @since 0.2
     */
    public IsEqualToJsonObject(@Nullable JsonObject expectedObject) {
        if (expectedObject != null) {
            for (Map.Entry<String, JsonElement> prop : expectedObject.entrySet()) {
                prop(prop.getKey(), prop.getValue());
            }
        }
    }
}
