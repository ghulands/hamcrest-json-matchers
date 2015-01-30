package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
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
 * Unit test for {@link IsJsonString}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
@RunWith(Parameterized.class)
public class IsJsonStringTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new IsJsonString("Any"), JsonNull.INSTANCE, false},
                {new IsJsonString("string"), JsonNull.INSTANCE, false},

                {new IsJsonString("anyone"), new JsonPrimitive(42), false},
                {new IsJsonString("could"), new JsonPrimitive(-21), false},

                {new IsJsonString("possibly"), new JsonPrimitive(0), false},
                {new IsJsonString("ever"), new JsonPrimitive(0), false},

                {new IsJsonString("think"), new JsonPrimitive(3.141), false},
                {new IsJsonString("of"), new JsonPrimitive(-123.45), false},

                {new IsJsonString("in"), new JsonPrimitive(0.0), false},
                {new IsJsonString("his"), new JsonPrimitive(0.0), false},

                {new IsJsonString("or"), new JsonPrimitive(true), false},
                {new IsJsonString("her"), new JsonPrimitive(false), false},

                {new IsJsonString("entire"), object().build(), false},
                {new IsJsonString("life"), object().build(), false},

                {new IsJsonString("on"), array().build(), false},
                {new IsJsonString("earth."), array().build(), false},

                {new IsJsonString(""), new JsonPrimitive(""), true},
                {new IsJsonString(""), new JsonPrimitive("Not empty"), false},
                {new IsJsonString("Not empty"), new JsonPrimitive(""), false},
        });
    }

    @Parameterized.Parameter(0)
    public IsJsonString matcher;

    @Parameterized.Parameter(1)
    public Object actual;

    @Parameterized.Parameter(2)
    public boolean result;

    @Test
    public void testMatches() {

        boolean match = matcher.matches(actual);

        Description desc = new StringDescription();
        desc.appendText("expected:\n");
        matcher.describeTo(desc);
        desc.appendText("\nbut:\n");
        matcher.describeMismatch(actual, desc);


        assertThat(desc.toString(), match, equalTo(result));
    }
}