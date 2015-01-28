package de.crunc.hamcrest.json.converter;


import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import de.crunc.hamcrest.json.JsonArrayBuilder;
import de.crunc.hamcrest.json.JsonObjectBuilder;
import de.crunc.hamcrest.json.VertxJsonArrayBuilder;
import de.crunc.hamcrest.json.VertxJsonObjectBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Unit test for {@link VertxJsonObjectToGsonConverter}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class VertxJsonObjectToGsonConverterTest {

    private VertxJsonObjectToGsonConverter converter;

    @Before
    public void setUp() {
        converter = new VertxJsonObjectToGsonConverter();
    }

    @Test
    public void shouldConvertEmptyObject() {
        org.vertx.java.core.json.JsonObject vertxObject = VertxJsonObjectBuilder.object()
                .build();

        JsonElement result = converter.toJsonElement(vertxObject);

        assertThat(result, instanceOf(JsonObject.class));
        assertThat(((JsonObject) result), equalTo(new JsonObject()));
    }

    @Test
    public void shouldConvertNullObject() {
        JsonElement result = converter.toJsonElement(null);

        assertThat(result, instanceOf(JsonNull.class));
        assertThat(((JsonNull) result), equalTo(JsonNull.INSTANCE));
    }

    @Test
    public void shouldConvertObject() {
        org.vertx.java.core.json.JsonObject vertxObject = VertxJsonObjectBuilder.object()
                .put("booleanTrue", true)
                .put("booleanFalse", false)
                .put("positiveInteger", 42)
                .put("negativeInteger", -7)
                .put("zeroInteger", 0)
                .put("positiveFloat", 3112.17)
                .put("negativeFloat", -0.33)
                .put("zeroFloat", 0.0)
                .put("string", "My fake plants died because I did not pretend to water them. - Mitch Hedberg")
                .put("emptyString", "")
                .put("object", VertxJsonObjectBuilder.object()
                        .put("foo", "bar")
                        .put("value", 1337))
                .put("emptyObject", VertxJsonObjectBuilder.object())
                .put("array", VertxJsonArrayBuilder.array()
                        .add("Read this!")
                        .add(3.141))
                .putNull("null")
                .build();

        JsonElement result = converter.toJsonElement(vertxObject);

        assertThat(result, instanceOf(JsonObject.class));
        assertThat(((JsonObject) result), equalTo(JsonObjectBuilder.object()
                .put("booleanTrue", true)
                .put("booleanFalse", false)
                .put("positiveInteger", 42)
                .put("negativeInteger", -7)
                .put("zeroInteger", 0)
                .put("positiveFloat", 3112.17)
                .put("negativeFloat", -0.33)
                .put("zeroFloat", 0.0)
                .put("string", "My fake plants died because I did not pretend to water them. - Mitch Hedberg")
                .put("emptyString", "")
                .put("object", JsonObjectBuilder.object()
                        .put("foo", "bar")
                        .put("value", 1337))
                .put("emptyObject", JsonObjectBuilder.object())
                .put("array", JsonArrayBuilder.array()
                        .add("Read this!")
                        .add(3.141))
                .putNull("null")
                .build()));
    }
}