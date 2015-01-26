package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Base {@link Matcher} for matching {@link JsonPrimitive} which contain a {@link Boolean} value.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public abstract class BaseJsonBooleanMatcher extends BaseJsonPrimitiveMatcher {

    @Override
    protected boolean matchesSafely(JsonPrimitive primitive, Description mismatchDescription, int indent) {
        if (!primitive.isBoolean()) {
            mismatchDescription.appendText("not a boolean value ")
                    .appendValue(primitive.getAsString());
            return false;
        }

        return matchesSafely(primitive.getAsBoolean(), mismatchDescription, indent);
    }

    protected abstract boolean matchesSafely(Boolean value, Description mismatchDescription, int indent);
}
