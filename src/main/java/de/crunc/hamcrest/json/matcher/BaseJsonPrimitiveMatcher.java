package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Base {@link Matcher} for matching {@link JsonPrimitive} values.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public abstract class BaseJsonPrimitiveMatcher extends BaseJsonElementMatcher {

    @Override
    protected boolean matchesSafely(JsonElement item, Description mismatchDescription, int indent) {

        if (!item.isJsonPrimitive()) {
            mismatchDescription.appendText("not a primitive ").appendValue(item);
            return false;
        }

        return matchesSafely((JsonPrimitive)item, mismatchDescription, indent);
    }

    protected abstract boolean matchesSafely(JsonPrimitive primitive, Description mismatchDescription, int indent);
}
