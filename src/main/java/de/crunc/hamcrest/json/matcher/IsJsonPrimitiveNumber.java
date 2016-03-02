package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Matches {@link JsonPrimitive} with a non special numeric value (not NaN or Infinity etc.).
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.2
 */
public class IsJsonPrimitiveNumber extends BaseJsonPrimitiveMatcher {

    private final Matcher<? super BigDecimal> matcher;

    @SuppressWarnings("unchecked")
    public IsJsonPrimitiveNumber(@Nullable Matcher<? super BigDecimal> matcher) {
        this.matcher = matcher != null ? matcher : IsNull.nullValue();
    }

    @Override
    protected boolean matchesSafely(JsonPrimitive primitive, Description mismatchDescription, int indent) {
        final BigDecimal value;

        try {
            value = primitive.getAsBigDecimal();
        } catch (NumberFormatException e) {
            mismatchDescription.appendText("not a numeric value <")
                    .appendText(primitive.getAsString())
                    .appendText(">");
            return false;
        }

        if (!matcher.matches(value)) {
            matcher.describeMismatch(value, mismatchDescription);
            return false;
        }

        return true;
    }

    @Override
    protected void describeTo(Description description, int indent) {
        description.appendText("numeric value ")
                .appendDescriptionOf(matcher);
    }
}
