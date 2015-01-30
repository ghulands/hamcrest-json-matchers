package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.crunc.hamcrest.json.VertxJsonArrayBuilder;
import de.crunc.hamcrest.json.VertxJsonObjectBuilder;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

import static de.crunc.hamcrest.json.JsonArrayBuilder.array;
import static de.crunc.hamcrest.json.JsonObjectBuilder.object;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for {@link IsEqualToJsonArray}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
@RunWith(Parameterized.class)
public class IsEqualToJsonArrayTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new IsEqualToJsonArray(), JsonNull.INSTANCE, false},
                {new IsEqualToJsonArray(), new JsonPrimitive(true), false},
                {new IsEqualToJsonArray(), new JsonPrimitive(false), false},
                {new IsEqualToJsonArray(), new JsonPrimitive(42), false},
                {new IsEqualToJsonArray(), new JsonPrimitive(3.1), false},
                {new IsEqualToJsonArray(), new JsonPrimitive("HalliGalli"), false},
                {new IsEqualToJsonArray(), new JsonObject(), false},

                {new IsEqualToJsonArray(array().add(712).build()), array().add(712).build(), true},
                {new IsEqualToJsonArray(array().add(713).build()), array().add(712).build(), false},
                {new IsEqualToJsonArray(array().add(712L).build()), array().add(712).build(), true},
                {new IsEqualToJsonArray(array().add(711L).build()), array().add(712).build(), false},
                {new IsEqualToJsonArray(array().add(712.0f).build()), array().add(712).build(), true},
                {new IsEqualToJsonArray(array().add(712.1f).build()), array().add(712).build(), false},
                {new IsEqualToJsonArray(array().add(712.0).build()), array().add(712).build(), true},
                {new IsEqualToJsonArray(array().add(711.99).build()), array().add(712).build(), false},
                {new IsEqualToJsonArray(array().add(new BigDecimal("712.00")).build()), array().add(712).build(), true},
                {new IsEqualToJsonArray(array().add(new BigDecimal("712.001")).build()), array().add(712).build(), false},
                {new IsEqualToJsonArray(array().add(new BigInteger("712")).build()), array().add(712).build(), true},
                {new IsEqualToJsonArray(array().add(new BigInteger("612")).build()), array().add(712).build(), false},

                {new IsEqualToJsonArray(array().add(-9138).build()), array().add(-9138.379).build(), false},
                {new IsEqualToJsonArray(array().add(-9139).build()), array().add(-9138.379).build(), false},
                {new IsEqualToJsonArray(array().add(-9138L).build()), array().add(-9138.379).build(), false},
                {new IsEqualToJsonArray(array().add(-9139L).build()), array().add(-9138.379).build(), false},
                {new IsEqualToJsonArray(array().add(-9138.379f).build()), array().add(-9138.379).build(), true},
                {new IsEqualToJsonArray(array().add(-9138.378f).build()), array().add(-9138.379).build(), false},
                {new IsEqualToJsonArray(array().add(-9138.379).build()), array().add(-9138.379).build(), true},
                {new IsEqualToJsonArray(array().add(-9138.389).build()), array().add(-9138.379).build(), false},
                {new IsEqualToJsonArray(array().add(new BigDecimal("-9138.379")).build()), array().add(-9138.379).build(), true},
                {new IsEqualToJsonArray(array().add(new BigDecimal("-9138.3792")).build()), array().add(-9138.379).build(), false},
                {new IsEqualToJsonArray(array().add(new BigInteger("9138")).build()), array().add(-9138.379).build(), false},
                {new IsEqualToJsonArray(array().add(new BigInteger("9139")).build()), array().add(-9138.379).build(), false},

                {new IsEqualToJsonArray(array().add(true).build()), array().add(true).build(), true},
                {new IsEqualToJsonArray(array().add(false).build()), array().add(true).build(), false},

                {new IsEqualToJsonArray(array().add(false).build()), array().add(false).build(), true},
                {new IsEqualToJsonArray(array().add(true).build()), array().add(false).build(), false},

                {new IsEqualToJsonArray(array().add("A string").build()), array().add("A string").build(), true},
                {new IsEqualToJsonArray(array().add("Another string").build()), array().add("A string").build(), false},

                {new IsEqualToJsonArray(array().add("").build()), array().add("").build(), true},
                {new IsEqualToJsonArray(array().add("Not empty").build()), array().add("").build(), false},

                {new IsEqualToJsonArray(array().addNull().build()), array().addNull().build(), true},
                {new IsEqualToJsonArray(array().add("null").build()), array().addNull().build(), false},

                {new IsEqualToJsonArray(array().build()), array().add(true).build(), false},
                {new IsEqualToJsonArray(array().build()), array().add(false).build(), false},
                {new IsEqualToJsonArray(array().build()), array().add(0).build(), false},
                {new IsEqualToJsonArray(array().build()), array().add(0.73).build(), false},
                {new IsEqualToJsonArray(array().build()), array().add("Oh my gosh!").build(), false},
                {new IsEqualToJsonArray(array().build()), array().addNull().build(), false},

                {new IsEqualToJsonArray(array().add(true).build()), array().build(), false},
                {new IsEqualToJsonArray(array().add(false).build()), array().build(), false},
                {new IsEqualToJsonArray(array().add(13).build()), array().build(), false},
                {new IsEqualToJsonArray(array().add(13.37).build()), array().build(), false},
                {new IsEqualToJsonArray(array().add("Where did that come from?").build()), array().build(), false},
                {new IsEqualToJsonArray(array().addNull().build()), array().build(), false},

                // -----------------------------------------------------------------------------------------------
                // GSON
                // -----------------------------------------------------------------------------------------------
                {new IsEqualToJsonArray(), array().build(), true},

                {
                        new IsEqualToJsonArray(array()
                                .add("A day without sunshine is like, you know, night. - Steve Martin")
                                .add(65000)
                                .add(19.17)
                                .add(true)
                                .add(false)
                                .addNull()
                                .add(object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .add(array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .build()),
                        array()
                                .add("A day without sunshine is like, you know, night. - Steve Martin")
                                .add(65000)
                                .add(19.17)
                                .add(true)
                                .add(false)
                                .addNull()
                                .add(object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .add(array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .build(),
                        true
                },

                // -----------------------------------------------------------------------------------------------
                // JSON string
                // -----------------------------------------------------------------------------------------------
                {new IsEqualToJsonArray(), array().encode(), true},

                {
                        new IsEqualToJsonArray(array()
                                .add("A day without sunshine is like, you know, night. - Steve Martin")
                                .add(65000)
                                .add(19.17)
                                .add(true)
                                .add(false)
                                .addNull()
                                .add(object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .add(array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .build()),
                        array()
                                .add("A day without sunshine is like, you know, night. - Steve Martin")
                                .add(65000)
                                .add(19.17)
                                .add(true)
                                .add(false)
                                .addNull()
                                .add(object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .add(array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .encode(),
                        true
                },

                // -----------------------------------------------------------------------------------------------
                // json.org
                // -----------------------------------------------------------------------------------------------
                {new IsEqualToJsonArray(), VertxJsonArrayBuilder.array().build(), true},

                {
                        new IsEqualToJsonArray(array()
                                .add("A day without sunshine is like, you know, night. - Steve Martin")
                                .add(65000)
                                .add(19.17)
                                .add(true)
                                .add(false)
                                .addNull()
                                .add(object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .add(array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .build()),
                        new JSONArray()
                                .put("A day without sunshine is like, you know, night. - Steve Martin")
                                .put(65000)
                                .put(19.17)
                                .put(true)
                                .put(false)
                                .put((Object) null)
                                .put(new JSONObject()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .put("embeddedNullValue", (Object) null))
                                .put(new JSONArray()
                                        .put("An embedded string")
                                        .put(7777)
                                        .put(512.1024)
                                        .put(true)
                                        .put(false)
                                        .put((Object) null)),
                        true
                },

                // -----------------------------------------------------------------------------------------------
                // Vert.x
                // -----------------------------------------------------------------------------------------------
                {new IsEqualToJsonArray(), new JSONArray(), true},

                {
                        new IsEqualToJsonArray(array()
                                .add("A day without sunshine is like, you know, night. - Steve Martin")
                                .add(65000)
                                .add(19.17)
                                .add(true)
                                .add(false)
                                .addNull()
                                .add(object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .add(array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .build()),
                        VertxJsonArrayBuilder.array()
                                .add("A day without sunshine is like, you know, night. - Steve Martin")
                                .add(65000)
                                .add(19.17)
                                .add(true)
                                .add(false)
                                .addNull()
                                .add(VertxJsonObjectBuilder.object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .add(VertxJsonArrayBuilder.array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .build(),
                        true
                },
        });
    }

    @Parameterized.Parameter(0)
    public IsEqualToJsonArray matcher;

    @Parameterized.Parameter(1)
    public Object actual;

    @Parameterized.Parameter(2)
    public boolean result;

    @Test
    public void testMatches() {

        boolean match = matcher.matches(actual);

        Description desc = new StringDescription();
        desc.appendText("expected <");
        if (actual != null) {
            desc.appendText(actual.getClass().getSimpleName());
        } else {
            desc.appendText("null");
        }
        desc.appendText(">:\n");
        
        matcher.describeTo(desc);
        desc.appendText("\nbut:\n");
        matcher.describeMismatch(actual, desc);

        assertThat(desc.toString(), match, equalTo(result));
    }
}