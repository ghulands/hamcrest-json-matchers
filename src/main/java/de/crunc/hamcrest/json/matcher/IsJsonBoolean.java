package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;

import javax.annotation.Nullable;

/**
 * Simple {@link Matcher} for matching a {@link JsonPrimitive} which contains a {@link Boolean} value.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonBoolean extends BaseJsonBooleanMatcher {

    private final Matcher<? super Boolean> matcher;

    public IsJsonBoolean(@Nullable Boolean expected) {
        if (expected != null) {
            this.matcher = new IsEqual<Boolean>(expected);
        } else {
            this.matcher = new IsNull<Boolean>();
        }
    }

    @SuppressWarnings("unchecked")
    public IsJsonBoolean(@Nullable Matcher<? super Boolean> matcher) {
        this.matcher = (Matcher<? super Boolean>)(matcher != null ? matcher : new IsNull<Boolean>());
    }

    @Override
    protected boolean matchesSafely(Boolean value, Description mismatchDescription, int indent) {

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
