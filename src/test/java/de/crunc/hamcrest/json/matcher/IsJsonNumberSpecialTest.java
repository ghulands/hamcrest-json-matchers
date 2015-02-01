package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link IsJsonNumber}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonNumberSpecialTest {

    @Test
    public void shouldMatchFloatNaN() {
        assertThat(new JsonPrimitive(Float.NaN), new IsJsonNumber(Float.NaN));
    }

    @Test
    public void shouldMatchFloatPositiveInfinity() {
        assertThat(new JsonPrimitive(Float.POSITIVE_INFINITY), new IsJsonNumber(Float.POSITIVE_INFINITY));
    }

    @Test
    public void shouldMatchFloatNegativeInfinity() {
        assertThat(new JsonPrimitive(Float.NEGATIVE_INFINITY), new IsJsonNumber(Float.NEGATIVE_INFINITY));
    }

    @Test
    public void shouldMatchDoubleNaN() {
        assertThat(new JsonPrimitive(Double.NaN), new IsJsonNumber(Double.NaN));
    }

    @Test
    public void shouldMatchDoublePositiveInfinity() {
        assertThat(new JsonPrimitive(Double.POSITIVE_INFINITY), new IsJsonNumber(Double.POSITIVE_INFINITY));
    }

    @Test
    public void shouldMatchDoubleNegativeInfinity() {
        assertThat(new JsonPrimitive(Double.NEGATIVE_INFINITY), new IsJsonNumber(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void shouldMatchStringNaNToFloatNan() {
        assertThat(new JsonPrimitive("NaN"), new IsJsonNumber(Float.NaN));
    }

    @Test
    public void shouldMatchStringPositiveInfinityToFloatPositiveInfinity() {
        assertThat(new JsonPrimitive("Infinity"), new IsJsonNumber(Float.POSITIVE_INFINITY));
    }

    @Test
    public void shouldMatchStringNegativeInfinityToFloatNegativeInfinity() {
        assertThat(new JsonPrimitive("-Infinity"), new IsJsonNumber(Float.NEGATIVE_INFINITY));
    }

    @Test
    public void shouldMatchStringNaNToDoubleNan() {
        assertThat(new JsonPrimitive("NaN"), new IsJsonNumber(Double.NaN));
    }

    @Test
    public void shouldMatchStringPositiveInfinityToDoublePositiveInfinity() {
        assertThat(new JsonPrimitive("Infinity"), new IsJsonNumber(Double.POSITIVE_INFINITY));
    }

    @Test
    public void shouldMatchStringNegativeInfinityToDoubleNegativeInfinity() {
        assertThat(new JsonPrimitive("-Infinity"), new IsJsonNumber(Double.NEGATIVE_INFINITY));
    }
}
