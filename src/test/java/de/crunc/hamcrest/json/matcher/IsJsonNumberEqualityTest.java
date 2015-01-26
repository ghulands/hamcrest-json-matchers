package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for {@link IsJsonNumber}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
@RunWith(Parameterized.class)
public class IsJsonNumberEqualityTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, 0, true}, // int <-> int
                {7, 77, false}, // int <-> int

                {3L, 3L, true}, // long <-> long
                {-1L, 1L, false}, // long <-> long

                {113.3379f, 113.3379f, true}, // float <-> float
                {3.141f, -7.221f, false}, // float <-> float

                {99.978, 99.978, true}, // double <-> double
                {-99.978, 99.978, false}, // double <-> double

                {15371L, 15371, true}, // long <-> int
                {15372L, 15371, false}, // long <-> int

                {-1337, -1337L, true}, // int <-> long
                {-1338, -1337L, false}, // int <-> long

                {3543.0f, 3543, true}, // float <-> int
                {3543.5f, 3543, false}, // float <-> int

                {-678, -678.0f, true}, // int <-> float
                {-678, -678.1f, false}, // int <-> float

                {123.0, 123, true}, // double <-> int
                {123.001, 123, false}, // double <-> int

                {-73, -73.0, true}, // int <-> double
                {-74, -73.9999999999999, false}, // int <-> double

                {32L, 32.0f, true}, // long <-> float
                {32L, 32.2f, false}, // long <-> float

                {-1.0f, -1L, true}, // float <-> long
                {-0.9999f, -1L, false}, // float <-> long

                {67L, 67.0, true}, // long <-> double
                {67L, 67.000001, false}, // long <-> double

                {-987654321.0, -987654321L, true}, // double <-> long
                {-987654321.9, -987654321L, false}, // double <-> long

                {42.131f, 42.131, true}, // float <-> double
                {42.131f, 42.13, false}, // float <-> double

                {-12345.9, -12345.9f, true}, // double <-> float
                {-12345.9, -12345.98f, false}, // double <-> float
        });
    }

    @Parameterized.Parameter(0)
    public Number actual;

    @Parameterized.Parameter(1)
    public Number expected;

    @Parameterized.Parameter(2)
    public boolean result;

    @Test
    public void testMatches() {
        IsJsonNumber matcher = new IsJsonNumber(expected);
        JsonElement element = new JsonPrimitive(actual);

        assertThat(actual + " <> " + expected, matcher.matches(element), equalTo(result));
    }
}