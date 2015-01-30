package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
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
 * Unit test for {@link IsEqualToJsonObject}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
@RunWith(Parameterized.class)
public class IsEqualToJsonObjectTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new IsEqualToJsonObject(), JsonNull.INSTANCE, false},
                {new IsEqualToJsonObject(), new JsonPrimitive(true), false},
                {new IsEqualToJsonObject(), new JsonPrimitive(false), false},
                {new IsEqualToJsonObject(), new JsonPrimitive(42), false},
                {new IsEqualToJsonObject(), new JsonPrimitive(3.1), false},
                {new IsEqualToJsonObject(), new JsonPrimitive("HalliGalli"), false},
                {new IsEqualToJsonObject(), new JsonArray(), false},

                {new IsEqualToJsonObject(object().put("aFloat", 712).build()), object().put("aFloat", 712).build(), true},
                {new IsEqualToJsonObject(object().put("aFloat", 713).build()), object().put("aFloat", 712).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", 712L).build()), object().put("aFloat", 712).build(), true},
                {new IsEqualToJsonObject(object().put("aFloat", 711L).build()), object().put("aFloat", 712).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", 712.0f).build()), object().put("aFloat", 712).build(), true},
                {new IsEqualToJsonObject(object().put("aFloat", 712.1f).build()), object().put("aFloat", 712).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", 712.0).build()), object().put("aFloat", 712).build(), true},
                {new IsEqualToJsonObject(object().put("aFloat", 711.99).build()), object().put("aFloat", 712).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", new BigDecimal("712.00")).build()), object().put("aFloat", 712).build(), true},
                {new IsEqualToJsonObject(object().put("aFloat", new BigDecimal("712.001")).build()), object().put("aFloat", 712).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", new BigInteger("712")).build()), object().put("aFloat", 712).build(), true},
                {new IsEqualToJsonObject(object().put("aFloat", new BigInteger("612")).build()), object().put("aFloat", 712).build(), false},

                {new IsEqualToJsonObject(object().put("aFloat", -9138).build()), object().put("aFloat", -9138.379).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", -9139).build()), object().put("aFloat", -9138.379).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", -9138L).build()), object().put("aFloat", -9138.379).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", -9139L).build()), object().put("aFloat", -9138.379).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", -9138.379f).build()), object().put("aFloat", -9138.379).build(), true},
                {new IsEqualToJsonObject(object().put("aFloat", -9138.378f).build()), object().put("aFloat", -9138.379).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", -9138.379).build()), object().put("aFloat", -9138.379).build(), true},
                {new IsEqualToJsonObject(object().put("aFloat", -9138.389).build()), object().put("aFloat", -9138.379).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", new BigDecimal("-9138.379")).build()), object().put("aFloat", -9138.379).build(), true},
                {new IsEqualToJsonObject(object().put("aFloat", new BigDecimal("-9138.3792")).build()), object().put("aFloat", -9138.379).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", new BigInteger("9138")).build()), object().put("aFloat", -9138.379).build(), false},
                {new IsEqualToJsonObject(object().put("aFloat", new BigInteger("9139")).build()), object().put("aFloat", -9138.379).build(), false},

                {new IsEqualToJsonObject(object().put("booleanTrue", true).build()), object().put("booleanTrue", true).build(), true},
                {new IsEqualToJsonObject(object().put("booleanTrue", false).build()), object().put("booleanTrue", true).build(), false},

                {new IsEqualToJsonObject(object().put("booleanFalse", false).build()), object().put("booleanFalse", false).build(), true},
                {new IsEqualToJsonObject(object().put("booleanFalse", true).build()), object().put("booleanFalse", false).build(), false},

                {new IsEqualToJsonObject(object().put("aString", "A string").build()), object().put("aString", "A string").build(), true},
                {new IsEqualToJsonObject(object().put("aString", "Another string").build()), object().put("aString", "A string").build(), false},

                {new IsEqualToJsonObject(object().put("emptyString", "").build()), object().put("emptyString", "").build(), true},
                {new IsEqualToJsonObject(object().put("emptyString", "Nt empty").build()), object().put("emptyString", "").build(), false},

                {new IsEqualToJsonObject(object().putNull("nullValue").build()), object().putNull("nullValue").build(), true},
                {new IsEqualToJsonObject(object().put("nullValue", "null").build()), object().putNull("nullValue").build(), false},

                {new IsEqualToJsonObject(), object().put("unexpectedTrue", true).build(), false},
                {new IsEqualToJsonObject(), object().put("unexpectedFalse", false).build(), false},
                {new IsEqualToJsonObject(), object().put("unexpectedInteger", 0).build(), false},
                {new IsEqualToJsonObject(), object().put("unexpectedFloat", 0.73).build(), false},
                {new IsEqualToJsonObject(), object().put("unexpectedString", "Oh my gosh!").build(), false},
                {new IsEqualToJsonObject(), object().putNull("unexpectedNull").build(), false},

                {new IsEqualToJsonObject(object().put("unmatchedTrue", true).build()), object().build(), false},
                {new IsEqualToJsonObject(object().put("unmatchedFalse", false).build()), object().build(), false},
                {new IsEqualToJsonObject(object().put("unmatchedInteger", 13).build()), object().build(), false},
                {new IsEqualToJsonObject(object().put("unmatchedFloat", 13.37).build()), object().build(), false},
                {new IsEqualToJsonObject(object().put("unmatchedString", "Where did that come from?").build()), object().build(), false},

                {new IsEqualToJsonObject(object().putNull("unmatchedNull").build()), object().build(), true},

                // -----------------------------------------------------------------------------------------------
                // GSON
                // -----------------------------------------------------------------------------------------------
                {new IsEqualToJsonObject(), object().build(), true},

                {
                        new IsEqualToJsonObject(object()
                                .put("aString", "A day without sunshine is like, you know, night. - Steve Martin")
                                .put("aFloat", 65000)
                                .put("aFloat", 19.17)
                                .put("booleanTrue", true)
                                .put("booleanFalse", false)
                                .putNull("nullValue")
                                .put("anObject", object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .put("anArray", array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .build()),
                        object()
                                .put("aString", "A day without sunshine is like, you know, night. - Steve Martin")
                                .put("aFloat", 65000)
                                .put("aFloat", 19.17)
                                .put("booleanTrue", true)
                                .put("booleanFalse", false)
                                .putNull("nullValue")
                                .put("anObject", object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .put("anArray", array()
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
                {new IsEqualToJsonObject(), object().encode(), true},

                {
                        new IsEqualToJsonObject(object()
                                .put("aString", "A day without sunshine is like, you know, night. - Steve Martin")
                                .put("aFloat", 65000)
                                .put("aFloat", 19.17)
                                .put("booleanTrue", true)
                                .put("booleanFalse", false)
                                .putNull("nullValue")
                                .put("anObject", object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .put("anArray", array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .build()),
                        object()
                                .put("aString", "A day without sunshine is like, you know, night. - Steve Martin")
                                .put("aFloat", 65000)
                                .put("aFloat", 19.17)
                                .put("booleanTrue", true)
                                .put("booleanFalse", false)
                                .putNull("nullValue")
                                .put("anObject", object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .put("anArray", array()
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
                // Vertx JSON
                // -----------------------------------------------------------------------------------------------
                {new IsEqualToJsonObject(), VertxJsonObjectBuilder.object().build(), true},

                {
                        new IsEqualToJsonObject(object()
                                .put("aString", "A day without sunshine is like, you know, night. - Steve Martin")
                                .put("aFloat", 65000)
                                .put("aFloat", 19.17)
                                .put("booleanTrue", true)
                                .put("booleanFalse", false)
                                .putNull("nullValue")
                                .put("anObject", object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .put("anArray", array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .build()),
                        VertxJsonObjectBuilder.object()
                                .put("aString", "A day without sunshine is like, you know, night. - Steve Martin")
                                .put("aFloat", 65000)
                                .put("aFloat", 19.17)
                                .put("booleanTrue", true)
                                .put("booleanFalse", false)
                                .putNull("nullValue")
                                .put("anObject", VertxJsonObjectBuilder.object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .put("anArray", VertxJsonArrayBuilder.array()
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
                {new IsEqualToJsonObject(), new JSONObject(), true},

                {
                        new IsEqualToJsonObject(object()
                                .put("aString", "A day without sunshine is like, you know, night. - Steve Martin")
                                .put("aFloat", 65000)
                                .put("aFloat", 19.17)
                                .put("booleanTrue", true)
                                .put("booleanFalse", false)
                                .putNull("nullValue")
                                .put("anObject", object()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .putNull("embeddedNullValue"))
                                .put("anArray", array()
                                        .add("An embedded string")
                                        .add(7777)
                                        .add(512.1024)
                                        .add(true)
                                        .add(false)
                                        .addNull())
                                .build()),
                        new JSONObject()
                                .put("aString", "A day without sunshine is like, you know, night. - Steve Martin")
                                .put("aFloat", 65000)
                                .put("aFloat", 19.17)
                                .put("booleanTrue", true)
                                .put("booleanFalse", false)
                                .put("nullValue", (Object) null)
                                .put("anObject", new JSONObject()
                                        .put("embeddedString", "An embedded string")
                                        .put("embeddedInteger", 319)
                                        .put("embeddedFloat", 99.91)
                                        .put("embeddedBooleanTrue", true)
                                        .put("embeddedBooleanFalse", false)
                                        .put("embeddedNullValue", (Object) null))
                                .put("anArray", new JSONArray()
                                        .put("An embedded string")
                                        .put(7777)
                                        .put(512.1024)
                                        .put(true)
                                        .put(false)
                                        .put((Object) null)),
                        true
                },
        });
    }

    @Parameterized.Parameter(0)
    public IsEqualToJsonObject matcher;

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