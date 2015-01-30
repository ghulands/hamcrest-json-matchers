package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import javax.annotation.Nullable;

/**
 * Matches {@link JsonArray} if it is equal to the given array.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.2
 */
public class IsEqualToJsonArray<T> extends IsJsonArray<T> {

    /**
     * Creates a new matcher that checks for equality to an empty array.
     *
     * @since 0.2
     */
    public IsEqualToJsonArray() {
        this(null);
    }

    /**
     * Creates a new matcher that checks for equality to the expected array. If the expected array is {@code null} then
     * this matcher checks for equality to an empty array.
     *
     * @param expectedArray The expected array. Can be {@code null}.
     * @since 0.2
     */
    public IsEqualToJsonArray(@Nullable JsonArray expectedArray) {
        if (expectedArray != null) {
            for (JsonElement item : expectedArray) {
                item(item);
            }
        }
    }
}
