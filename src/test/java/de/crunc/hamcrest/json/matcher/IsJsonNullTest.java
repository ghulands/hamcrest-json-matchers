package de.crunc.hamcrest.json.matcher;

import com.google.gson.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for {@link IsJsonNull}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
@RunWith(Parameterized.class)
public class IsJsonNullTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {JsonNull.INSTANCE, true},
                {new JsonPrimitive(true), false},
                {new JsonPrimitive(false), false},
                {new JsonPrimitive(42), false},
                {new JsonPrimitive(3.1), false},
                {new JsonPrimitive("HalliGalli"), false},
                {new JsonObject(), false},
                {new JsonArray(), false}
        });
    }

    @Parameterized.Parameter(0)
    public JsonElement value;

    @Parameterized.Parameter(1)
    public boolean result;

    @Test
    public void testMatches() {
        assertThat(new IsJsonNull().matches(value), equalTo(result));
    }
}