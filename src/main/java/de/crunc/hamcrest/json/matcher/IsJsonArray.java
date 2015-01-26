package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;

import java.util.List;

/**
 * Matches a {@link List} if the items of the list match the expected items.
 *
 * @author "Hauke Jaeger, hauke.jaeger@googlemail.com"
 */
public class IsJsonArray extends BaseJsonArrayMatcher<JsonArray> {

    @Override
    protected JsonArray toJsonArray(JsonArray item) {
        return item;
    }
}
