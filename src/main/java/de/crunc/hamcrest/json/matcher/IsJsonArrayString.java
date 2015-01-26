package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * Matches JSON strings of the form {@code "[ <item>, <item>, <item>, ... ]"}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonArrayString extends BaseJsonArrayMatcher<String> {

    @Override
    protected JsonArray toJsonArray(String item) {
        return new JsonParser().parse(item).getAsJsonArray();
    }
}
