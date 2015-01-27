package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.hamcrest.Description;

/**
 * Matches JSON strings of the form {@code "[ <item>, <item>, <item>, ... ]"}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonArrayString extends BaseJsonArrayMatcher<String> {

    @Override
    protected JsonArray toJsonArray(String item, Description mismatchDescription) {
        return new JsonParser().parse(item).getAsJsonArray();
    }
}
