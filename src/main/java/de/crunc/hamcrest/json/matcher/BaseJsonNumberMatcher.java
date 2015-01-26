package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.math.BigDecimal;

/**
 * Base {@link Matcher} for matching {@link JsonPrimitive} which contain a {@link Number} value.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public abstract class BaseJsonNumberMatcher extends BaseJsonPrimitiveMatcher {

    @Override
    protected boolean matchesSafely(JsonPrimitive primitive, Description mismatchDescription, int indent) {
        if (!primitive.isNumber()) {
            mismatchDescription.appendText("not a numeric value ")
                    .appendValue(primitive.getAsString());
            return false;
        }

        return matchesSafely(primitive.getAsBigDecimal(), mismatchDescription, indent);
    }

    protected abstract boolean matchesSafely(BigDecimal value, Description mismatchDescription, int indent);
}
