package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.crunc.hamcrest.json.converter.GsonConversionException;
import de.crunc.hamcrest.json.converter.GsonConverter;
import org.hamcrest.Description;

/**
 * Matches {@link JsonObject}s.
 *
 * @author "Hauke Jaeger, hauke.jaeger@googlemail.com"
 * @since 0.1
 */
public class IsJsonObject<T> extends BaseJsonObjectMatcher<T> {

    @Override
    protected JsonObject toJsonObject(T item, Description mismatchDescription) {

        final JsonElement element;

        try {
            element = GsonConverter.convert(item);
        } catch (GsonConversionException e) {
            mismatchDescription.appendText("expected JSON object but: " + e.getMessage());
            return null;
        }

        if (element.isJsonObject()) {
            return (JsonObject) element;
        }

        mismatchDescription.appendText("expected JSON but got ")
                .appendValue(item.getClass().getSimpleName());
        return null;
    }
}
