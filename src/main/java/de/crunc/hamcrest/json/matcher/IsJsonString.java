package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;

import javax.annotation.Nullable;

/**
 * Simple {@link Matcher} for matching a {@link JsonPrimitive} which contains a {@link String} value.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.1
 */
public class IsJsonString extends BaseJsonStringMatcher {

    private final Matcher<? super String> matcher;

    public IsJsonString(@Nullable String expected) {
        if (expected != null) {
            this.matcher = new IsEqual<>(expected);
        } else {
            this.matcher = new IsNull<>();
        }
    }

    @SuppressWarnings("unchecked")
    public IsJsonString(@Nullable Matcher<? super String> matcher) {
        this.matcher = (Matcher<? super String>) (matcher != null ? matcher : new IsNull<String>());
    }

    @Override
    protected boolean matchesSafely(String value, Description mismatchDescription, int indent) {

        if (!matcher.matches(value)) {
            matcher.describeMismatch(value, mismatchDescription);
            return false;
        }

        return true;
    }

    @Override
    protected void describeTo(Description description, int indent) {
        matcher.describeTo(description);
    }
}
