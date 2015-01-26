package de.crunc.hamcrest.json.matcher;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.Description;

/**
 * matches {@link JsonObject}s.
 *
 * @author "Hauke Jaeger, hauke.jaeger@googlemail.com"
 */
public class IsJsonObject extends BaseJsonObjectMatcher<JsonElement> {

    @Override
    protected JsonObject toJsonObject(JsonElement item, Description mismatchDescription) {
        if (item.isJsonObject()) {
            return (JsonObject) item;
        }

        mismatchDescription.appendText("expected JsonObject but got ")
                .appendValue(item.getClass().getSimpleName());
        return null;
    }
}
