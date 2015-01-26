package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Matches a {@link JsonElement} which is equal to an expected {@link JsonArray}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonElementEqualToJsonArray extends TypeSafeDiagnosingMatcher<JsonElement> {

    private final JsonArray expected;

    public IsJsonElementEqualToJsonArray(@Nullable JsonArray expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("JsonObject ").appendValue(expected);
    }

    @Override
    protected boolean matchesSafely(JsonElement element, Description mismatchDescription) {
        if (!element.isJsonArray()) {
            mismatchDescription.appendText("not a JsonArray: ")
                    .appendValue(element);
            return false;
        }

        if (!Objects.equals(element, expected)) {
            mismatchDescription.appendText("was ")
                    .appendValue(element);
            return false;
        }

        return true;
    }
}
