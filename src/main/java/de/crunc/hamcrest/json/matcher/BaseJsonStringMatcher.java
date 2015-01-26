package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Base {@link Matcher} for matching {@link JsonPrimitive} which contain a {@link String} value.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public abstract class BaseJsonStringMatcher extends BaseJsonPrimitiveMatcher {

    @Override
    protected boolean matchesSafely(JsonPrimitive primitive, Description mismatchDescription, int indent) {
        if (!primitive.isString()) {
            mismatchDescription.appendText("not a string value ")
                    .appendValue(primitive.getAsString());
            return false;
        }

        return matchesSafely(primitive.getAsString(), mismatchDescription, indent);
    }

    protected abstract boolean matchesSafely(String value, Description mismatchDescription, int indent);
}
