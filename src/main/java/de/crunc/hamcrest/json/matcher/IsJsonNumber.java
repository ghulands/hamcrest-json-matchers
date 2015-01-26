package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.hamcrest.number.BigDecimalCloseTo;
import org.hamcrest.number.OrderingComparison;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Simple {@link Matcher} for matching a {@link JsonPrimitive} which contains a {@link Number} value.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonNumber extends BaseJsonNumberMatcher {

    private final Matcher<? super BigDecimal> matcher;

    public IsJsonNumber(@Nullable Number expected) {
        if (expected != null) {
            BigDecimal expectedDec = new BigDecimal(expected.toString());
            this.matcher = OrderingComparison.comparesEqualTo(expectedDec);
        } else {
            this.matcher = new IsNull<>();
        }
    }

    @SuppressWarnings("unchecked")
    public IsJsonNumber(@Nullable Matcher<? super BigDecimal> matcher) {
        this.matcher = (Matcher<? super BigDecimal>)(matcher != null ? matcher : new IsNull<>());
    }

    @Override
    protected boolean matchesSafely(BigDecimal value, Description mismatchDescription, int indent) {

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
