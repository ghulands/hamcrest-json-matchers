package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
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
 * Unit test for {@link IsJsonObject}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
@RunWith(Parameterized.class)
public class IsJsonObjectTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new IsJsonObject(), JsonNull.INSTANCE, false},
                {new IsJsonObject(), new JsonPrimitive(true), false},
                {new IsJsonObject(), new JsonPrimitive(false), false},
                {new IsJsonObject(), new JsonPrimitive(42), false},
                {new IsJsonObject(), new JsonPrimitive(3.1), false},
                {new IsJsonObject(), new JsonPrimitive("HalliGalli"), false},
                {new IsJsonObject(), new JsonArray(), false},

                {new IsJsonObject(), object().build(), true},

                {
                        new IsJsonObject()
                                .prop("aString", "A day without sunshine is like, you know, night. - Steve Martin")
                                .prop("aFloat", 65000)
                                .prop("aFloat", 19.17)
                                .prop("booleanTrue", true)
                                .prop("booleanFalse", false)
                                .prop("nullValue", null)
                                .prop("anObject", new IsJsonObject()
                                        .prop("embeddedString", "An embedded string")
                                        .prop("embeddedInteger", 319)
                                        .prop("embeddedFloat", 99.91)
                                        .prop("embeddedBooleanTrue", true)
                                        .prop("embeddedBooleanFalse", false)
                                        .prop("embeddedNullValue", null))
                                .prop("anArray", new IsJsonArray()
                                        .item("An embedded string")
                                        .item(7777)
                                        .item(512.1024)
                                        .item(true)
                                        .item(false)
                                        .item(null)),
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

                {new IsJsonObject().prop("aFloat", 712), object().put("aFloat", 712).build(), true},
                {new IsJsonObject().prop("aFloat", 713), object().put("aFloat", 712).build(), false},
                {new IsJsonObject().prop("aFloat", 712L), object().put("aFloat", 712).build(), true},
                {new IsJsonObject().prop("aFloat", 711L), object().put("aFloat", 712).build(), false},
                {new IsJsonObject().prop("aFloat", 712.0f), object().put("aFloat", 712).build(), true},
                {new IsJsonObject().prop("aFloat", 712.1f), object().put("aFloat", 712).build(), false},
                {new IsJsonObject().prop("aFloat", 712.0), object().put("aFloat", 712).build(), true},
                {new IsJsonObject().prop("aFloat", 711.99), object().put("aFloat", 712).build(), false},
                {new IsJsonObject().prop("aFloat", new BigDecimal("712.00")), object().put("aFloat", 712).build(), true},
                {new IsJsonObject().prop("aFloat", new BigDecimal("712.001")), object().put("aFloat", 712).build(), false},
                {new IsJsonObject().prop("aFloat", new BigInteger("712")), object().put("aFloat", 712).build(), true},
                {new IsJsonObject().prop("aFloat", new BigInteger("612")), object().put("aFloat", 712).build(), false},

                {new IsJsonObject().prop("aFloat", -9138), object().put("aFloat", -9138.379).build(), false},
                {new IsJsonObject().prop("aFloat", -9139), object().put("aFloat", -9138.379).build(), false},
                {new IsJsonObject().prop("aFloat", -9138L), object().put("aFloat", -9138.379).build(), false},
                {new IsJsonObject().prop("aFloat", -9139L), object().put("aFloat", -9138.379).build(), false},
                {new IsJsonObject().prop("aFloat", -9138.379f), object().put("aFloat", -9138.379).build(), true},
                {new IsJsonObject().prop("aFloat", -9138.378f), object().put("aFloat", -9138.379).build(), false},
                {new IsJsonObject().prop("aFloat", -9138.379), object().put("aFloat", -9138.379).build(), true},
                {new IsJsonObject().prop("aFloat", -9138.389), object().put("aFloat", -9138.379).build(), false},
                {new IsJsonObject().prop("aFloat", new BigDecimal("-9138.379")), object().put("aFloat", -9138.379).build(), true},
                {new IsJsonObject().prop("aFloat", new BigDecimal("-9138.3792")), object().put("aFloat", -9138.379).build(), false},
                {new IsJsonObject().prop("aFloat", new BigInteger("9138")), object().put("aFloat", -9138.379).build(), false},
                {new IsJsonObject().prop("aFloat", new BigInteger("9139")), object().put("aFloat", -9138.379).build(), false},

                {new IsJsonObject().prop("booleanTrue", true), object().put("booleanTrue", true).build(), true},
                {new IsJsonObject().prop("booleanTrue", false), object().put("booleanTrue", true).build(), false},

                {new IsJsonObject().prop("booleanFalse", false), object().put("booleanFalse", false).build(), true},
                {new IsJsonObject().prop("booleanFalse", true), object().put("booleanFalse", false).build(), false},

                {new IsJsonObject().prop("aString", "A string"), object().put("aString", "A string").build(), true},
                {new IsJsonObject().prop("aString", "Another string"), object().put("aString", "A string").build(), false},

                {new IsJsonObject().prop("emptyString", ""), object().put("emptyString", "").build(), true},
                {new IsJsonObject().prop("emptyString", "Nt empty"), object().put("emptyString", "").build(), false},

                {new IsJsonObject().prop("nullValue", null), object().putNull("nullValue").build(), true},
                {new IsJsonObject().prop("nullValue", "null"), object().putNull("nullValue").build(), false},

                {new IsJsonObject(), object().put("unexpectedTrue", true).build(), false},
                {new IsJsonObject(), object().put("unexpectedFalse", false).build(), false},
                {new IsJsonObject(), object().put("unexpectedInteger", 0).build(), false},
                {new IsJsonObject(), object().put("unexpectedFloat", 0.73).build(), false},
                {new IsJsonObject(), object().put("unexpectedString", "Oh my gosh!").build(), false},
                {new IsJsonObject(), object().putNull("unexpectedNull").build(), false},

                {new IsJsonObject().prop("unmatchedTrue", true), object().build(), false},
                {new IsJsonObject().prop("unmatchedFalse", false), object().build(), false},
                {new IsJsonObject().prop("unmatchedInteger", 13), object().build(), false},
                {new IsJsonObject().prop("unmatchedFloat", 13.37), object().build(), false},
                {new IsJsonObject().prop("unmatchedString", "Where did that come from?"), object().build(), false},
                {new IsJsonObject().prop("unmatchedNull", null), object().build(), false},
        });
    }

    @Parameterized.Parameter(0)
    public IsJsonObject matcher;

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