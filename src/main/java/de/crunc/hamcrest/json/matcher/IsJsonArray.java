package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.crunc.hamcrest.json.converter.GsonConversionException;
import de.crunc.hamcrest.json.converter.GsonConverter;
import org.hamcrest.Description;

import java.util.List;

/**
 * Matches a {@link List} if the items of the list match the expected items.
 *
 * @author "Hauke Jaeger, hauke.jaeger@googlemail.com"
 */
public class IsJsonArray<T> extends BaseJsonArrayMatcher<T> {

    @Override
    protected JsonArray toJsonArray(T item, Description mismatchDescription) {

        final JsonElement element;

        try {
            element = GsonConverter.convert(item);
        } catch (GsonConversionException e) {
            mismatchDescription.appendText("expected JSON array but: " + e.getMessage());
            return null;
        }

        if (element.isJsonArray()) {
            return (JsonArray) element;
        }

        mismatchDescription.appendText("expected JSON array but got ")
                .appendValue(item.getClass().getSimpleName());
        return null;
    }
}
