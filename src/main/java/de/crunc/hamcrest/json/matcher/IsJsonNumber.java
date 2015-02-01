package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.number.OrderingComparison;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Simple {@link Matcher} for matching a {@link JsonPrimitive} which contains a {@link Number} value.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonNumber extends BaseJsonPrimitiveMatcher {

    private final Matcher<? super JsonPrimitive> matcher;

    public IsJsonNumber(@Nullable Number expected) {
        if (expected == null) {
            this.matcher = new IsJsonNull();
        } else if (expected instanceof Float) {
            float f = (Float)expected;
            if (Float.isNaN(f)) {
                this.matcher = new IsJsonPrimitiveNaN();
            } else if (Float.isInfinite(f) && f > 0) {
                this.matcher = new IsJsonPrimitivePositiveInfinity();
            } else if (Float.isInfinite(f) && f < 0) {
                this.matcher = new IsJsonPrimitiveNegativeInfinity();
            } else {
                BigDecimal expectedDec = new BigDecimal(expected.toString());
                this.matcher = new IsJsonPrimitiveNumber(OrderingComparison.comparesEqualTo(expectedDec));
            }
        } else if (expected instanceof Double) {
            double d = (Double)expected;
            if (Double.isNaN(d)) {
                this.matcher = new IsJsonPrimitiveNaN();
            } else if (Double.isInfinite(d) && d > 0) {
                this.matcher = new IsJsonPrimitivePositiveInfinity();
            } else if (Double.isInfinite(d) && d < 0) {
                this.matcher = new IsJsonPrimitiveNegativeInfinity();
            } else {
                BigDecimal expectedDec = new BigDecimal(expected.toString());
                this.matcher = new IsJsonPrimitiveNumber(OrderingComparison.comparesEqualTo(expectedDec));
            }
        } else {
            BigDecimal expectedDec = new BigDecimal(expected.toString());
            this.matcher = new IsJsonPrimitiveNumber(OrderingComparison.comparesEqualTo(expectedDec));
        }
    }

    public IsJsonNumber(@Nullable Matcher<? super BigDecimal> matcher) {
        this.matcher = matcher != null ? new IsJsonPrimitiveNumber(matcher) : new IsJsonNull();
    }

    @Override
    protected boolean matchesSafely(JsonPrimitive value, Description mismatchDescription, int indent) {

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
