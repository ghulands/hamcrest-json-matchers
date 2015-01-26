package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Simple {@link Matcher} for matching a {@link JsonPrimitive} which contains a {@code null} value.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonNull extends BaseJsonElementMatcher {

    @Override
    protected boolean matchesSafely(JsonElement primitive, Description mismatchDescription, int indent) {

        if (!primitive.isJsonNull()) {
            mismatchDescription.appendText("not null ")
                    .appendValue(primitive);
            return false;
        }

        return true;
    }

    @Override
    protected void describeTo(Description description, int indent) {
        description.appendText("null");
    }
}
