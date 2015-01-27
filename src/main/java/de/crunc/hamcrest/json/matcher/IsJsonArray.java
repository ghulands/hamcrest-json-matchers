package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.Description;

import java.util.List;

/**
 * Matches a {@link List} if the items of the list match the expected items.
 *
 * @author "Hauke Jaeger, hauke.jaeger@googlemail.com"
 */
public class IsJsonArray extends BaseJsonArrayMatcher<JsonElement> {

    @Override
    protected JsonArray toJsonArray(JsonElement item, Description mismatchDescription) {
        if (item.isJsonArray()) {
            return (JsonArray) item;
        }

        mismatchDescription.appendText("expected JSON array but got ")
                .appendValue(item.getClass().getSimpleName());
        return null;
    }
}
