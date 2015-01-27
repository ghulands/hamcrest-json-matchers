package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Base class for matching JSON arrays.
 *
 * @author "Hauke Jaeger, hauke.jaeger@googlemail.com"
 */
public abstract class BaseJsonArrayMatcher<T> extends BaseJsonStructureMatcher<T> {

    private final List<Matcher<?>> itemMatchers = new ArrayList<>();

    @Override
    public void describeTo(Description description, int indent) {
        description.appendText("[");

        for (int i = 0; i < itemMatchers.size(); ++i) {
            Matcher<?> itemMatcher = itemMatchers.get(i);
            description.appendText("\n")
                    .appendDescriptionOf(indent())
                    .appendText("[").appendText(String.valueOf(i)).appendText("]: ")
                    .appendDescriptionOf(itemMatcher);
        }

        description.appendText("\n")
                .appendDescriptionOf(indent(-1))
                .appendText("]");
    }

    public BaseJsonArrayMatcher<T> item(@Nullable Object expected) {
        itemMatchers.add(getMatcher(expected));
        return this;
    }

    public BaseJsonArrayMatcher<T> items(@Nullable Iterable<Object> items) {
        if (items != null) {
            for (Object item : items) {
                item(item);
            }
        }
        return this;
    }

    public BaseJsonArrayMatcher<T> items(@Nullable Object... items) {
        if (items != null) {
            items(Arrays.asList(items));
        }
        return this;
    }

    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription, int indent) {
        JsonArray array = toJsonArray(item, mismatchDescription);
        return array != null && matchInternally(array, mismatchDescription, indent);
    }

    protected abstract JsonArray toJsonArray(T item, Description mismatchDescription);

    private boolean matchInternally(JsonArray items, Description mismatchDescription, int indent) {
        boolean match = true;

        mismatchDescription.appendText("[");

        for (int idx = 0; idx < items.size(); ++idx) {
            JsonElement element = items.get(idx);

            if (idx < itemMatchers.size()) {
                Matcher<?> itemMatcher = itemMatchers.get(idx);
                if (!matchesProperty(idx, itemMatcher, element, mismatchDescription, -1)) {
                    match = false;
                }

            } else {
                if (!matchesUnexpectedProperty(idx, element, mismatchDescription, -1)) {
                    match = false;
                }
            }
        }

        for (int idx = items.size(); idx < itemMatchers.size(); ++idx) {
            Matcher<?> itemMatcher = itemMatchers.get(idx);
            if (!matchesUnmatchedProperty(idx, itemMatcher, mismatchDescription, -1)) {
                match = false;
            }
        }

        mismatchDescription.appendText("\n")
                .appendDescriptionOf(indent(-1))
                .appendText("]");

        return match;
    }

    /**
     * Handles the matching of an item matcher when a corresponding item has been found in the array under test.
     *
     * @param index               The index of the item.
     * @param itemMatcher         The matcher for the value of the item.
     * @param actualElement       The value of the item.
     * @param mismatchDescription The mismatch description for recording errors.
     * @param indent              The current indent that can be used to format the mismatch description.
     * @return {@code true} if the property should be considered a match, {@code false otherwise}.
     * @since 0.1
     */
    protected boolean matchesProperty(
            int index,
            Matcher<?> itemMatcher,
            JsonElement actualElement,
            Description mismatchDescription,
            int indent) {

        if (!itemMatcher.matches(actualElement)) {
            mismatchDescription.appendText("\n")
                    .appendDescriptionOf(indent())
                    .appendText("[" + index + "]: ");
            itemMatcher.describeMismatch(actualElement, mismatchDescription);
            return false;
        }

        return true;
    }

    /**
     * Handles the matching of an item matcher when no corresponding item has been found in the object under test.
     *
     * @param index               The index of the item.
     * @param itemMatcher         The matcher for the value of the items.
     * @param mismatchDescription The mismatch description for recording errors.
     * @param indent              The current indent that can be used to format the mismatch description.
     * @return {@code true} if the items should be considered a match, {@code false otherwise}.
     * @since 0.1
     */
    protected boolean matchesUnmatchedProperty(
            int index,
            Matcher<?> itemMatcher,
            Description mismatchDescription,
            int indent) {

        mismatchDescription.appendText("\n")
                .appendDescriptionOf(indent())
                .appendText("[" + index + "]: unmatched ")
                .appendDescriptionOf(itemMatcher);
        return false;
    }

    /**
     * Handles the matching of an item when there is no corresponding matcher (the item was not expected).
     *
     * @param index               The index of the item.
     * @param unexpectedElement   The value of the item.
     * @param mismatchDescription The mismatch description for recording errors.
     * @param indent              The current indent that can be used to format the mismatch description.
     * @return {@code true} if the property should be considered a match, {@code false otherwise}.
     * @since 0.1
     */
    protected boolean matchesUnexpectedProperty(
            int index,
            JsonElement unexpectedElement,
            Description mismatchDescription,
            int indent) {

        mismatchDescription.appendText("\n")
                .appendDescriptionOf(indent())
                .appendText("[" + index + "]: unexpected ")
                .appendValue(String.valueOf(unexpectedElement));
        return false;
    }
}
