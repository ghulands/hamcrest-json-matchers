package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;

/**
 * Matches {@link JsonPrimitive} which represents a -Infinity value.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.2
 */
public class IsJsonPrimitiveNegativeInfinity extends BaseJsonPrimitiveMatcher {

    @Override
    protected boolean matchesSafely(JsonPrimitive primitive, Description mismatchDescription, int indent) {
        final Double value = primitive.getAsDouble();

        if (!(Double.isInfinite(value) && value < 0)) {
            mismatchDescription.appendValue(value);
            return false;
        }

        return true;
    }

    @Override
    protected void describeTo(Description description, int indent) {
        description.appendText("-Infinity");
    }
}
