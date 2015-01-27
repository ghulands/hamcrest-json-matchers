package de.crunc.hamcrest.json.matcher;

import com.google.gson.*;
import de.crunc.hamcrest.json.JsonArrayBuilder;
import de.crunc.hamcrest.json.JsonObjectBuilder;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static de.crunc.hamcrest.json.JsonArrayBuilder.array;
import static de.crunc.hamcrest.json.JsonObjectBuilder.object;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for {@link IsJsonBoolean}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
@RunWith(Parameterized.class)
public class IsJsonBooleanTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new IsJsonBoolean(true), JsonNull.INSTANCE, false},
                {new IsJsonBoolean(false), JsonNull.INSTANCE, false},

                {new IsJsonBoolean(true), new JsonPrimitive(42), false},
                {new IsJsonBoolean(false), new JsonPrimitive(-21), false},

                {new IsJsonBoolean(true), new JsonPrimitive(0), false},
                {new IsJsonBoolean(false), new JsonPrimitive(0), false},

                {new IsJsonBoolean(true), new JsonPrimitive(3.141), false},
                {new IsJsonBoolean(false), new JsonPrimitive(-123.45), false},

                {new IsJsonBoolean(true), new JsonPrimitive(0.0), false},
                {new IsJsonBoolean(false), new JsonPrimitive(0.0), false},

                {new IsJsonBoolean(true), new JsonPrimitive("HalliGalli"), false},
                {new IsJsonBoolean(false), new JsonPrimitive("HalliGalli"), false},

                {new IsJsonBoolean(true), object().build(), false},
                {new IsJsonBoolean(false), object().build(), false},

                {new IsJsonBoolean(true), array().build(), false},
                {new IsJsonBoolean(false), array().build(), false},

                {new IsJsonBoolean(true), new JsonPrimitive(true), true},
                {new IsJsonBoolean(false), new JsonPrimitive(true), false},

                {new IsJsonBoolean(true), new JsonPrimitive(false), false},
                {new IsJsonBoolean(false), new JsonPrimitive(false), true},
        });
    }

    @Parameterized.Parameter(0)
    public IsJsonBoolean matcher;

    @Parameterized.Parameter(1)
    public JsonElement objectUnderTest;

    @Parameterized.Parameter(2)
    public boolean result;

    @Test
    public void testMatches() {

        boolean match = matcher.matches(objectUnderTest);

        Description desc = new StringDescription();
        desc.appendText("expected:\n");
        matcher.describeTo(desc);
        desc.appendText("\nbut:\n");
        matcher.describeMismatch(objectUnderTest, desc);


        assertThat(desc.toString(), match, equalTo(result));
    }
}