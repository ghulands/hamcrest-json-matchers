package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

/**
 * Base class for {@link Matcher} which match either {@link JsonObject} or {@link JsonArray}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public abstract class BaseJsonStructureMatcher<T> extends BaseJsonMatcher<T> {

    /**
     * Get the most appropriate matcher for the given value when it is matched against a {@link JsonElement} value.
     */
    protected Matcher<?> getMatcher(Object expected) {
        if (expected == null) {
            return new IsJsonNull();

        } else if (expected instanceof Matcher) {
            return (Matcher<?>) expected;

        } else if (expected instanceof JsonObject) {
            return new IsJsonElementEqualToJsonObject((JsonObject) expected);

        } else if (expected instanceof JsonArray) {
            return new IsJsonElementEqualToJsonArray((JsonArray) expected);

        } else if (expected instanceof Boolean) {
            return new IsJsonBoolean((Boolean) expected);

        } else if (expected instanceof Number) {
            return new IsJsonNumber((Number) expected);

        } else if (expected instanceof String) {
            return new IsJsonString((String) expected);

        } else {
            return new IsEqual<Object>(expected);
        }
    }
}
