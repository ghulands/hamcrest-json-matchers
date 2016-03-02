package de.crunc.hamcrest.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.crunc.hamcrest.json.converter.GsonConverter;
import de.crunc.hamcrest.json.matcher.*;
import org.hamcrest.Matcher;
import org.hamcrest.number.BigDecimalCloseTo;

import java.math.BigDecimal;

/**
 * Provides {@link Matcher} for matching JSON values.
 */
public final class JsonMatchers {

    /**
     * Matches any object that can be converted to a {@link JsonObject} by the {@link GsonConverter}.
     *
     * @param <T> The type of the object under test.
     * @return A new matcher.
     * @since 0.1
     */
    public static <T> IsJsonObject<T> isJsonObject() {
        return new IsJsonObject<>();
    }

    /**
     * Matches any object that can be converted to a {@link JsonArray} by the {@link GsonConverter}.
     *
     * @param <T> The type of the object under test.
     * @return A new matcher.
     * @since 0.1
     */
    public static <T> IsJsonArray<T> isJsonArray() {
        return new IsJsonArray<>();
    }

    /**
     * Matches a {@link JsonPrimitive} which wraps a boolean value that equals the given value.
     *
     * @param value The expected value.
     * @return A new matcher.
     * @since 0.2
     */
    public static IsJsonBoolean isJsonBoolean(Boolean value) {
        return new IsJsonBoolean(value);
    }

    /**
     * Matches a {@link JsonPrimitive} which wraps a boolean value that matches the given matcher.
     *
     * @param matcher The matcher for the boolean value,
     * @return A new matcher.
     * @since 0.2
     */
    public static IsJsonBoolean isJsonBoolean(Matcher<? super Boolean> matcher) {
        return new IsJsonBoolean(matcher);
    }

    /**
     * Matches a {@link JsonPrimitive} which wraps a numeric value that compares equal to the given value when it is
     * converted to a {@link BigDecimal}.
     *
     * @param value The expected value.
     * @return A new matcher.
     * @since 0.2
     */
    public static IsJsonNumber isJsonNumber(Number value) {
        return new IsJsonNumber(value);
    }

    /**
     * Matches a {@link JsonPrimitive} which wraps a numeric value that matches the given matcher when it is converted
     * to a {@link BigDecimal}.
     *
     * @param matcher The matcher for the numeric value,
     * @return A new matcher.
     * @since 0.2
     */
    public static IsJsonNumber isJsonNumber(Matcher<? super BigDecimal> matcher) {
        return new IsJsonNumber(matcher);
    }

    /**
     * Matches a {@link JsonPrimitive} which wraps a numeric value that is close to the given value within the range of
     * the given error when it is converted to a {@link BigDecimal}.
     *
     * @param expected The expected value.
     * @param error    The tolerated error (distance from the expected value).
     * @return A new matcher.
     * @since 0.2
     */
    public static IsJsonNumber isJsonNumber(Double expected, Double error) {
        return isJsonNumber(BigDecimal.valueOf(expected), BigDecimal.valueOf(error));
    }

    /**
     * Matches a {@link JsonPrimitive} which wraps a numeric value that is close to the given value within the range of
     * the given error when it is converted to a {@link BigDecimal}.
     *
     * @param expected The expected value.
     * @param error    The tolerated error (distance from the expected value).
     * @return A new matcher.
     * @since 0.2
     */
    public static IsJsonNumber isJsonNumber(Float expected, Float error) {
        return isJsonNumber(BigDecimal.valueOf(expected), BigDecimal.valueOf(error));
    }

    /**
     * Matches a {@link JsonPrimitive} which wraps a numeric value that is close to the given value within the range of
     * the given error when it is converted to a {@link BigDecimal}.
     *
     * @param expected The expected value.
     * @param error    The tolerated error (distance from the expected value).
     * @return A new matcher.
     * @since 0.2
     */
    public static IsJsonNumber isJsonNumber(BigDecimal expected, BigDecimal error) {
        return new IsJsonNumber(new BigDecimalCloseTo(expected, error));
    }

    /**
     * Matches a {@link JsonPrimitive} which wraps a string value that equals the given value.
     *
     * @param value The expected value.
     * @return A new matcher.
     * @since 0.2
     */
    public static IsJsonString isJsonString(String value) {
        return new IsJsonString(value);
    }

    /**
     * Matches a {@link JsonPrimitive} which wraps a string value that matches the given matcher.
     *
     * @param matcher The matcher for the numeric value,
     * @return A new matcher.
     * @since 0.2
     */
    public static IsJsonString isJsonString(Matcher<? super String> matcher) {
        return new IsJsonString(matcher);
    }

    /**
     * Matches a {@link JsonNull} value.
     *
     * @return A new matcher.
     * @since 0.2
     */
    public static IsJsonNull isJsonNull() {
        return new IsJsonNull();
    }

    private JsonMatchers() {
        throw new UnsupportedOperationException(JsonMatchers.class.getName() + " may not be instantiated");
    }
}
