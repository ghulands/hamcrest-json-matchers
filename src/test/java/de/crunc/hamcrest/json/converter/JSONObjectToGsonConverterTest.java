package de.crunc.hamcrest.json.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.crunc.hamcrest.json.JsonArrayBuilder;
import de.crunc.hamcrest.json.JsonObjectBuilder;
import de.crunc.hamcrest.json.VertxJsonArrayBuilder;
import de.crunc.hamcrest.json.VertxJsonObjectBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Unit test for {@link JSONObjectToGsonConverter}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JSONObjectToGsonConverterTest {
    
    private JSONObjectToGsonConverter converter;

    @Before
    public void setUp() {
        converter = new JSONObjectToGsonConverter();
    }

    @Test
    public void shouldConvertEmptyObject() {
        JSONObject jsonObject = new JSONObject();

        JsonElement result = converter.toJsonElement(jsonObject);

        assertThat(result, instanceOf(JsonObject.class));
        assertThat(((JsonObject) result), equalTo(new JsonObject()));
    }

    @Test
    public void shouldConvertObject() {
        JSONObject jsonObject = new JSONObject()
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
                .put("object", new JSONObject()
                        .put("foo", "bar")
                        .put("value", 1337))
                .put("emptyObject", new JSONObject())
                .put("array", new JSONArray()
                        .put("Read this!")
                        .put(3.141))
                .put("emptyArray", new JSONArray());

        JsonElement result = converter.toJsonElement(jsonObject);

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
                .put("emptyArray", JsonArrayBuilder.array())
                .build()));
    }
}