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
 * Unit test for {@link IsJsonArray}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
@RunWith(Parameterized.class)
public class IsJsonArrayTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new IsJsonArray(), JsonNull.INSTANCE, false},
                {new IsJsonArray(), new JsonPrimitive(true), false},
                {new IsJsonArray(), new JsonPrimitive(false), false},
                {new IsJsonArray(), new JsonPrimitive(42), false},
                {new IsJsonArray(), new JsonPrimitive(3.1), false},
                {new IsJsonArray(), new JsonPrimitive("HalliGalli"), false},
                {new IsJsonArray(), new JsonObject(), false},

                {new IsJsonArray().item(712), array().add(712).build(), true},
                {new IsJsonArray().item(713), array().add(712).build(), false},
                {new IsJsonArray().item(712L), array().add(712).build(), true},
                {new IsJsonArray().item(711L), array().add(712).build(), false},
                {new IsJsonArray().item(712.0f), array().add(712).build(), true},
                {new IsJsonArray().item(712.1f), array().add(712).build(), false},
                {new IsJsonArray().item(712.0), array().add(712).build(), true},
                {new IsJsonArray().item(711.99), array().add(712).build(), false},
                {new IsJsonArray().item(new BigDecimal("712.00")), array().add(712).build(), true},
                {new IsJsonArray().item(new BigDecimal("712.001")), array().add(712).build(), false},
                {new IsJsonArray().item(new BigInteger("712")), array().add(712).build(), true},
                {new IsJsonArray().item(new BigInteger("612")), array().add(712).build(), false},

                {new IsJsonArray().item(-9138), array().add(-9138.379).build(), false},
                {new IsJsonArray().item(-9139), array().add(-9138.379).build(), false},
                {new IsJsonArray().item(-9138L), array().add(-9138.379).build(), false},
                {new IsJsonArray().item(-9139L), array().add(-9138.379).build(), false},
                {new IsJsonArray().item(-9138.379f), array().add(-9138.379).build(), true},
                {new IsJsonArray().item(-9138.378f), array().add(-9138.379).build(), false},
                {new IsJsonArray().item(-9138.379), array().add(-9138.379).build(), true},
                {new IsJsonArray().item(-9138.389), array().add(-9138.379).build(), false},
                {new IsJsonArray().item(new BigDecimal("-9138.379")), array().add(-9138.379).build(), true},
                {new IsJsonArray().item(new BigDecimal("-9138.3792")), array().add(-9138.379).build(), false},
                {new IsJsonArray().item(new BigInteger("9138")), array().add(-9138.379).build(), false},
                {new IsJsonArray().item(new BigInteger("9139")), array().add(-9138.379).build(), false},

                {new IsJsonArray().item(true), array().add(true).build(), true},
                {new IsJsonArray().item(false), array().add(true).build(), false},

                {new IsJsonArray().item(false), array().add(false).build(), true},
                {new IsJsonArray().item(true), array().add(false).build(), false},

                {new IsJsonArray().item("A string"), array().add("A string").build(), true},
                {new IsJsonArray().item("Another string"), array().add("A string").build(), false},

                {new IsJsonArray().item(""), array().add("").build(), true},
                {new IsJsonArray().item("Nt empty"), array().add("").build(), false},

                {new IsJsonArray().item(null), array().addNull().build(), true},
                {new IsJsonArray().item("null"), array().addNull().build(), false},

                {new IsJsonArray(), array().add(true).build(), false},
                {new IsJsonArray(), array().add(false).build(), false},
                {new IsJsonArray(), array().add(0).build(), false},
                {new IsJsonArray(), array().add(0.73).build(), false},
                {new IsJsonArray(), array().add("Oh my gosh!").build(), false},
                {new IsJsonArray(), array().addNull().build(), false},

                {new IsJsonArray().item(true), array().build(), false},
                {new IsJsonArray().item(false), array().build(), false},
                {new IsJsonArray().item(13), array().build(), false},
                {new IsJsonArray().item(13.37), array().build(), false},
                {new IsJsonArray().item("Where did that come from?"), array().build(), false},
                {new IsJsonArray().item(null), array().build(), false},

                // -----------------------------------------------------------------------------------------------
                // GSON
                // -----------------------------------------------------------------------------------------------
                {new IsJsonArray(), array().build(), true},

                {
                        new IsJsonArray()
                                .item("A day without sunshine is like, you know, night. - Steve Martin")
                                .item(65000)
                                .item(19.17)
                                .item(true)
                                .item(false)
                                .item(null)
                                .item(new IsJsonObject()
                                        .prop("embeddedString", "An embedded string")
                                        .prop("embeddedInteger", 319)
                                        .prop("embeddedFloat", 99.91)
                                        .prop("embeddedBooleanTrue", true)
                                        .prop("embeddedBooleanFalse", false)
                                        .prop("embeddedNullValue", null))
                                .item(new IsJsonArray()
                                        .item("An embedded string")
                                        .item(7777)
                                        .item(512.1024)
                                        .item(true)
                                        .item(false)
                                        .item(null)),
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
                {new IsJsonArray(), array().encode(), true},

                {
                        new IsJsonArray()
                                .item("A day without sunshine is like, you know, night. - Steve Martin")
                                .item(65000)
                                .item(19.17)
                                .item(true)
                                .item(false)
                                .item(null)
                                .item(new IsJsonObject()
                                        .prop("embeddedString", "An embedded string")
                                        .prop("embeddedInteger", 319)
                                        .prop("embeddedFloat", 99.91)
                                        .prop("embeddedBooleanTrue", true)
                                        .prop("embeddedBooleanFalse", false)
                                        .prop("embeddedNullValue", null))
                                .item(new IsJsonArray()
                                        .item("An embedded string")
                                        .item(7777)
                                        .item(512.1024)
                                        .item(true)
                                        .item(false)
                                        .item(null)),
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
                {new IsJsonArray(), VertxJsonArrayBuilder.array().build(), true},

                {
                        new IsJsonArray()
                                .item("A day without sunshine is like, you know, night. - Steve Martin")
                                .item(65000)
                                .item(19.17)
                                .item(true)
                                .item(false)
                                .item(null)
                                .item(new IsJsonObject()
                                        .prop("embeddedString", "An embedded string")
                                        .prop("embeddedInteger", 319)
                                        .prop("embeddedFloat", 99.91)
                                        .prop("embeddedBooleanTrue", true)
                                        .prop("embeddedBooleanFalse", false)
                                        .prop("embeddedNullValue", null))
                                .item(new IsJsonArray()
                                        .item("An embedded string")
                                        .item(7777)
                                        .item(512.1024)
                                        .item(true)
                                        .item(false)
                                        .item(null)),
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
                {new IsJsonArray(), new JSONArray(), true},

                {
                        new IsJsonArray()
                                .item("A day without sunshine is like, you know, night. - Steve Martin")
                                .item(65000)
                                .item(19.17)
                                .item(true)
                                .item(false)
                                .item(null)
                                .item(new IsJsonObject()
                                        .prop("embeddedString", "An embedded string")
                                        .prop("embeddedInteger", 319)
                                        .prop("embeddedFloat", 99.91)
                                        .prop("embeddedBooleanTrue", true)
                                        .prop("embeddedBooleanFalse", false)
                                        .prop("embeddedNullValue", null))
                                .item(new IsJsonArray()
                                        .item("An embedded string")
                                        .item(7777)
                                        .item(512.1024)
                                        .item(true)
                                        .item(false)
                                        .item(null)),
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
    public IsJsonArray matcher;

    @Parameterized.Parameter(1)
    public Object objectUnderTest;

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