package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Base class for all json matchers.
 *
 * @author "Hauke Jaeger, hauke.jaeger@googlemail.com"
 */
public abstract class BaseJsonMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

    private static int indent = 0;
    private static final String indention = "   ";

    protected SelfDescribing indent() {
        return new SelfDescribing() {
            @Override
            public void describeTo(Description description) {
                for (int i = 0; i < indent; ++i) {
                    description.appendText(indention);
                }
            }
        };
    }

    protected SelfDescribing indent(final int mod) {
        return new SelfDescribing() {
            @Override
            public void describeTo(Description description) {
                for (int i = 0; i < (indent + mod); ++i) {
                    description.appendText(indention);
                }
            }
        };
    }

    @Override
    public final void describeTo(Description description) {
        ++indent;

        try {
            describeTo(description, indent);
        } finally {
            --indent;
        }
    }

    protected abstract void describeTo(Description description, int indent);

    @Override
    protected final boolean matchesSafely(T item, Description mismatchDescription) {
        ++indent;

        try {
            return matchesSafely(item, mismatchDescription, indent);
        } finally {
            --indent;
        }
    }

    protected abstract boolean matchesSafely(T item, Description mismatchDescription, int indent);

    protected Object mapToActualValue(JsonElement jsonElement) {

        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;

        } else if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive primitive = jsonElement.getAsJsonPrimitive();

            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            } else if (primitive.isNumber()) {
                return primitive.getAsBigDecimal();
            } else {
                return primitive.getAsString();
            }

        } else {
            return jsonElement;
        }
    }

    protected boolean isPrimitive(Object value) {
        return value instanceof Number
                || value instanceof String
                || value instanceof Boolean;
    }
}
