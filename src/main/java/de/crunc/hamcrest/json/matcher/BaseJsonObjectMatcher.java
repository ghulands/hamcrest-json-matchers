package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Base class for matching JSON objects.
 *
 * @author "Hauke Jaeger, hauke.jaeger@googlemail.com"
 */
public abstract class BaseJsonObjectMatcher<T> extends BaseJsonStructureMatcher<T> {

    private final Map<String, Matcher<?>> propertyMatchers = new HashMap<>();

    public BaseJsonObjectMatcher<T> prop(String propertyName, Object expected) {
        Matcher<?> matcher = getMatcher(expected);
        propertyMatchers.put(propertyName, matcher);
        return this;
    }

    @Override
    public void describeTo(Description description, int indent) {
        description.appendText("{");

        for (String propertyName : propertyMatchers.keySet()) {
            Matcher<?> propertyMatcher = propertyMatchers.get(propertyName);
            description.appendText("\n")
                    .appendDescriptionOf(indent())
                    .appendText(propertyName)
                    .appendText(": ");
            propertyMatcher.describeTo(description);
        }

        description.appendText("\n")
                .appendDescriptionOf(indent(-1))
                .appendText("}");
    }

    protected abstract JsonObject toJsonObject(T item, Description mismatchDescription);

    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription, int indent) {
        JsonObject jsonObject = toJsonObject(item, mismatchDescription);

        if (jsonObject != null) {
            return matchInternally(jsonObject, mismatchDescription, indent);
        }

        return false;
    }

    private boolean matchInternally(JsonObject actual, Description mismatchDescription, int indent) {
        boolean match = true;
        mismatchDescription.appendText("{");

        for (String propertyName : propertyMatchers.keySet()) {
            Description desc = new StringDescription();
            boolean addPropertyDescription = false;

            desc.appendText("\n")
                    .appendDescriptionOf(indent())
                    .appendText(propertyName)
                    .appendText(": ");

            // -----------------------------------------------------
            // check whether property exists
            // -----------------------------------------------------
            if (!actual.has(propertyName)) {
                desc.appendText("not present");
                addPropertyDescription = true;

                match = false;
            } else {
                JsonElement element = actual.get(propertyName);
                Matcher<?> propertyMatcher = propertyMatchers.get(propertyName);

                if (propertyMatcher instanceof IsJsonPrimitiveEqualToPrimitive) {
                    if (!element.isJsonPrimitive()) {
                        mismatchDescription.appendText("was no primitive value ")
                                .appendValue(element);
                        return false;
                    }

                    if (!propertyMatcher.matches(element)) {
                        propertyMatcher.describeMismatch(element, desc);
                        return false;
                    }
                }

                // -----------------------------------------------------
                // check whether the property matches
                // -----------------------------------------------------
                if (!propertyMatcher.matches(element)) {
                    propertyMatcher.describeMismatch(element, desc);
                    addPropertyDescription = true;

                    match = false;
                }
            }

            if (addPropertyDescription) {
                mismatchDescription.appendText(desc.toString());
            }
        }

        Set<String> unexpectedPropertyNames = new HashSet<>();

        for (Map.Entry<String, JsonElement> entry: actual.entrySet()) {
            if (!propertyMatchers.containsKey(entry.getKey())) {
                unexpectedPropertyNames.add(entry.getKey());
            }
        }

        for (String propertyName : unexpectedPropertyNames) {
            Description desc = new StringDescription();

            mismatchDescription.appendText("\n")
                    .appendDescriptionOf(indent())
                    .appendText(propertyName)
                    .appendText(": unexpected");

            match = false;
        }

        mismatchDescription.appendText("\n")
                .appendDescriptionOf(indent(-1))
                .appendText("}");

        return match;
    }
}
