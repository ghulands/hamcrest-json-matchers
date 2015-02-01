package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Matches {@link JsonPrimitive} which represents a NaN value.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.2
 */
public class IsJsonPrimitiveNaN extends BaseJsonPrimitiveMatcher {

    @Override
    protected boolean matchesSafely(JsonPrimitive primitive, Description mismatchDescription, int indent) {
        final Double value = primitive.getAsDouble();

        if (!Double.isNaN(value)) {
            mismatchDescription.appendText("not NaN but ")
                    .appendValue(value);
            return false;
        }

        return true;
    }

    @Override
    protected void describeTo(Description description, int indent) {
        description.appendText("NaN");
    }
}
