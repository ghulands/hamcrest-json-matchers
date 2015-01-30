package de.crunc.hamcrest.json.matcher;

import com.google.gson.*;
import org.hamcrest.Matcher;

/**
 * Base class for {@link Matcher} which match either {@link JsonObject} or {@link JsonArray}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.1
 */
public abstract class BaseJsonStructureMatcher<T> extends BaseJsonMatcher<T> {

    /**
     * Find the most appropriate {@link Matcher} for the given value when it is matched against a {@link JsonElement}.
     * <pre>
     * {@link Matcher} -&gt; itself
     * {@code null} -&gt; {@link IsJsonNull}
     * {@link JsonNull} -&gt; {@link IsJsonNull}
     * {@link JsonObject} -&gt; {@link IsEqualToJsonObject}
     * {@link JsonArray} -&gt; {@link IsEqualToJsonArray}
     * {@link JsonPrimitive} -&gt; {@link IsJsonBoolean} / {@link IsJsonNumber} / {@link IsJsonString}
     * {@link Boolean} -&gt; {@link IsJsonBoolean}
     * {@link Number} -&gt; {@link IsJsonNumber}
     * {@link String} -&gt; {@link IsJsonString}
     * Any other {@link Object} -&gt; throws {@link IllegalArgumentException}
     * </pre>
     *
     * @param expected The expected value. Can be a {@link Matcher} in which case the expected value is returned. Can be
     *                 {@code null} in which case {@link IsJsonNull} is returned.
     * @return A matcher for the expected value.
     * @throws IllegalArgumentException If the given expected value can not be converted to a {@link Matcher}.
     * @since 0.1
     */
    protected Matcher<?> getMatcher(Object expected) {
        if (expected == null) {
            return new IsJsonNull();
        } else if (expected instanceof Matcher) {
            return (Matcher<?>) expected;
        } else if (expected instanceof JsonObject) {
            return new IsEqualToJsonObject((JsonObject) expected);
        } else if (expected instanceof JsonArray) {
            return new IsEqualToJsonArray((JsonArray) expected);
        } else if (expected instanceof JsonPrimitive) {
            JsonPrimitive primitive = (JsonPrimitive) expected;
            if (primitive.isBoolean()) {
                return new IsJsonBoolean(primitive.getAsBoolean());
            } else if (primitive.isNumber()) {
                return new IsJsonNumber(primitive.getAsNumber());
            } else {
                return new IsJsonString(primitive.getAsString());
            }
        } else if (expected instanceof JsonNull) {
            return new IsJsonNull();
        } else if (expected instanceof Boolean) {
            return new IsJsonBoolean((Boolean) expected);
        } else if (expected instanceof Number) {
            return new IsJsonNumber((Number) expected);
        } else if (expected instanceof String) {
            return new IsJsonString((String) expected);
        } else {
            throw new IllegalArgumentException("unsupported JSON element: " + expected);
        }
    }
}
