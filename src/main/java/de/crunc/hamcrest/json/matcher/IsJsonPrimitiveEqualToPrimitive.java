package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Matches if a given expected can be considered equal to an actual {@link JsonElement} which it is compared to.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonPrimitiveEqualToPrimitive<T> extends BaseJsonPrimitiveMatcher{

    private final T expected;

    public IsJsonPrimitiveEqualToPrimitive(@Nullable T expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description description, int indent) {
        description.appendValue(expected);
    }

    @Override
    protected boolean matchesSafely(JsonPrimitive primitive, Description mismatchDescription, int indent) {

        if (expected == null && !primitive.isJsonNull()) {
            mismatchDescription.appendText("was not null ")
                    .appendValue(primitive.getAsString());
            return false;
        }

        else if (expected instanceof Boolean) {
            if (!primitive.isBoolean()) {
                mismatchDescription.appendText("was not a boolean ")
                        .appendValue(primitive.getAsString());
                return false;
            }

            if ((Boolean) expected != primitive.getAsBoolean()) {
                mismatchDescription.appendText("was ")
                        .appendValue(primitive.getAsString());
                return false;
            }
        }

        else if (expected instanceof Number) {
            if (!primitive.isNumber()) {
                mismatchDescription.appendText("was not a number ")
                        .appendValue(primitive.getAsString());
                return false;
            }

            BigDecimal decExpected = new BigDecimal(expected.toString());
            BigDecimal decValue = primitive.getAsBigDecimal();

            if (decExpected.compareTo(decValue) != 0) {
                mismatchDescription.appendText("was ")
                        .appendValue(decValue);
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether the given element is or represents {@code null}.
     */
    private boolean equalsNull(JsonPrimitive value) {
        return value == null || value.isJsonNull();
    }

    private String asString(JsonElement elem) {
        return elem != null ? elem.getAsString() : "null";
    }
}
