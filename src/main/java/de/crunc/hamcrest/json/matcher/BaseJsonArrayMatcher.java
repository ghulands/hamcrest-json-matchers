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

    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription, int indent) {
        return matchInternally(toJsonArray(item), mismatchDescription);
    }

    protected abstract JsonArray toJsonArray(T item);

    private boolean matchInternally(JsonArray items, Description mismatchDescription) {
        boolean match = true;

        mismatchDescription.appendText("[");

        for (int idx = 0; idx < items.size(); ++idx) {
            JsonElement element = items.get(idx);

            if (idx < itemMatchers.size()) {
                Matcher<?> itemMatcher = itemMatchers.get(idx);

                if (itemMatcher instanceof IsJsonPrimitiveEqualToPrimitive) {
                    if (!element.isJsonPrimitive()) {
                        mismatchDescription.appendText("was no primitive value ")
                                .appendValue(element);
                        return false;
                    }

                    if (!itemMatcher.matches(element)) {
                        itemMatcher.describeMismatch(element, mismatchDescription);
                        return false;
                    }
                }

                if (!itemMatcher.matches(element)) {
                    match = false;
                    mismatchDescription.appendText("\n")
                            .appendDescriptionOf(indent())
                            .appendText("["+ idx + "]: ");
                    itemMatcher.describeMismatch(element, mismatchDescription);
                }

            } else {
                match = false;
                mismatchDescription.appendText("\n")
                        .appendDescriptionOf(indent())
                        .appendText("[" + idx +  "]: unexpected ")
                        .appendValue(String.valueOf(element));
            }
        }

        for (int idx = items.size(); idx < itemMatchers.size(); ++idx) {
            match = false;
            mismatchDescription.appendText("\n")
                    .appendDescriptionOf(indent())
                    .appendText("[" + idx + "]: unmatched ")
                    .appendDescriptionOf(itemMatchers.get(idx));
        }

        mismatchDescription.appendText("\n")
                .appendDescriptionOf(indent(-1))
                .appendText("]");

        return match;
    }

    public BaseJsonArrayMatcher<T> item(@Nullable Matcher<?> itemMatcher) {
        itemMatchers.add(itemMatcher != null ? itemMatcher : new IsNull<>());
        return this;
    }

    public BaseJsonArrayMatcher<T> item(@Nullable Object expected) {
        return item(getMatcher(expected));
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
}
