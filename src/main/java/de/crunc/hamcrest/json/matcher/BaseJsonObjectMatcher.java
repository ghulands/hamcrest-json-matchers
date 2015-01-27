package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for matching JSON objects.
 *
 * @author "Hauke Jaeger, hauke.jaeger@googlemail.com"
 */
public abstract class BaseJsonObjectMatcher<T> extends BaseJsonStructureMatcher<T> {

    private final Map<String, Matcher<?>> propertyMatchers = new HashMap<>();

    public BaseJsonObjectMatcher<T> prop(String propertyName, Object expected) {
        if (propertyName == null || propertyName.trim().length() < 1) {
            throw new IllegalArgumentException("propertyName must not be null nor empty, but was <" + propertyName + ">");
        }

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

            Matcher<?> propertyMatcher = propertyMatchers.get(propertyName);

            if (!actual.has(propertyName)) {
                // -----------------------------------------------------
                // unmatched property name
                // -----------------------------------------------------
                if (!matchesUnmatchedProperty(propertyName, propertyMatcher, desc, indent)) {
                    addPropertyDescription = true;
                    match = false;
                }
            } else {
                // -----------------------------------------------------
                // matched property name
                // -----------------------------------------------------
                JsonElement element = actual.get(propertyName);
                if (!matchesProperty(propertyName, propertyMatcher, element, desc, indent)) {
                    addPropertyDescription = true;
                    match = false;
                }
            }

            if (addPropertyDescription) {
                mismatchDescription.appendText(desc.toString());
            }
        }

        for (Map.Entry<String, JsonElement> entry : actual.entrySet()) {
            if (!propertyMatchers.containsKey(entry.getKey())) {
                // -----------------------------------------------------
                // unexpected property name
                // -----------------------------------------------------
                String propertyName = entry.getKey();
                JsonElement element = entry.getValue();
                if (!matchesUnexpectedProperty(propertyName, element, mismatchDescription, indent)) {
                    match = false;
                }
            }
        }

        mismatchDescription.appendText("\n")
                .appendDescriptionOf(indent(-1))
                .appendText("}");

        return match;
    }

    /**
     * Handles the matching of a property matcher when a corresponding property has been found in the object under test.
     *
     * @param name                The name of the property.
     * @param propertyMatcher     The matcher for the value of the property.
     * @param actualElement       The value of the property.
     * @param mismatchDescription The mismatch description for recording errors.
     * @param indent              The current indent that can be used to format the mismatch description.
     * @return {@code true} if the property should be considered a match, {@code false otherwise}.
     * @since 0.1
     */
    protected boolean matchesProperty(
            String name,
            Matcher<?> propertyMatcher,
            JsonElement actualElement,
            Description mismatchDescription,
            int indent) {

        if (!propertyMatcher.matches(actualElement)) {
            propertyMatcher.describeMismatch(actualElement, mismatchDescription);
            return false;
        }

        return true;
    }

    /**
     * Handles the matching of a property matcher when no corresponding property has been found in the object under
     * test.
     *
     * @param name                The name of the property.
     * @param propertyMatcher     The matcher for the value of the property.
     * @param mismatchDescription The mismatch description for recording errors.
     * @param indent              The current indent that can be used to format the mismatch description.
     * @return {@code true} if the property should be considered a match, {@code false otherwise}.
     * @since 0.1
     */
    protected boolean matchesUnmatchedProperty(
            String name,
            Matcher<?> propertyMatcher,
            Description mismatchDescription,
            int indent) {
        mismatchDescription.appendText("not present");
        return false;
    }

    /**
     * Handles the matching of a property when there is no corresponding matcher (the property was not expected).
     *
     * @param name                The name of the property.
     * @param unexpectedElement   The value of the property.
     * @param mismatchDescription The mismatch description for recording errors.
     * @param indent              The current indent that can be used to format the mismatch description.
     * @return {@code true} if the property should be considered a match, {@code false otherwise}.
     * @since 0.1
     */
    protected boolean matchesUnexpectedProperty(
            String name,
            JsonElement unexpectedElement,
            Description mismatchDescription,
            int indent) {

        mismatchDescription.appendText("\n")
                .appendDescriptionOf(indent())
                .appendText(name)
                .appendText(": unexpected");
        return false;
    }
}
