package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.hamcrest.Matcher;
import org.hamcrest.number.BigDecimalCloseTo;

import java.math.BigDecimal;

/**
 * Provides {@link Matcher} for matching JSON values.
 */
public final class JsonMatchers {

    /**
     * Matches a JSON string.
     */
    public static IsJsonObjectString isJsonObjectString() {
        return new IsJsonObjectString();
    }

    /**
     * Matches a JSON string.
     */
    public static IsJsonArrayString isJsonArrayString() {
        return new IsJsonArrayString();
    }

    /**
     * Matches a {@link JsonObject}.
     */
    public static IsJsonObject isJsonObject() {
        return new IsJsonObject();
    }

    /**
     * Matches a {@link JsonObject}.
     */
    public static IsJsonArray isJsonArray() {
        return new IsJsonArray();
    }

    /**
     * Matches a {@link JsonPrimitive} which contains a number that is close to the given numeric string.
     */
    public static IsJsonNumber numberCloseTo(String expectedDecimal, String errorDecimal) {
        return numberCloseTo(new BigDecimal(expectedDecimal), new BigDecimal(errorDecimal));
    }

    /**
     * Matches a {@link JsonPrimitive} which contains a number that is close to the given numeric string.
     */
    public static IsJsonNumber numberCloseTo(Double expected, Double error) {
        return numberCloseTo(BigDecimal.valueOf(expected), BigDecimal.valueOf(error));
    }

    /**
     * Matches a {@link JsonPrimitive} which contains a number that is close to the given numeric string.
     */
    public static IsJsonNumber numberCloseTo(Float expected, Float error) {
        return numberCloseTo(BigDecimal.valueOf(expected), BigDecimal.valueOf(error));
    }

    /**
     * Matches a {@link JsonPrimitive} which contains a number that is close to the given number.
     */
    public static IsJsonNumber numberCloseTo(BigDecimal expected, BigDecimal error) {
        return new IsJsonNumber(new BigDecimalCloseTo(expected, error));
    }

    private JsonMatchers() {
        throw new UnsupportedOperationException(JsonMatchers.class.getName() + " may not be instantiated");
    }
}
