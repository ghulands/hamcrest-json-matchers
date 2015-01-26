package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.hamcrest.Description;

/**
 * Matches JSON strings like {@code {"prop":"value"}}.
 *
 * @author "Hauke Jaeger, hauke.jaeger@googlemail.com"
 */
public class IsJsonObjectString extends BaseJsonObjectMatcher<String> {

    @Override
    protected JsonObject toJsonObject(String item, Description mismatchDescription) {
        JsonElement element = new JsonParser().parse(item);
        return element.getAsJsonObject();
    }
}
