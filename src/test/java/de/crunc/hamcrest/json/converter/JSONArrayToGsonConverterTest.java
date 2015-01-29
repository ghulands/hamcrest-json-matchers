package de.crunc.hamcrest.json.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
 * Unit test for {@link JSONArrayToGsonConverter}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JSONArrayToGsonConverterTest {

    private JSONArrayToGsonConverter converter;

    @Before
    public void setUp() {
        converter = new JSONArrayToGsonConverter();
    }

    @Test
    public void shouldConvertEmptyArray() {
        JSONArray jsonArray = new JSONArray();

        JsonElement result = converter.toJsonElement(jsonArray);

        assertThat(result, instanceOf(JsonArray.class));
        assertThat(((JsonArray) result), equalTo(new JsonArray()));
    }

    @Test
    public void shouldConvertArray() {
        JSONArray jsonArray = new JSONArray()
                .put(true)
                .put(false)
                .put(42)
                .put(-7)
                .put(0)
                .put(3112.17)
                .put(-0.33)
                .put(0.0)
                .put("My fake plants died because I did not pretend to water them. - Mitch Hedberg")
                .put("")
                .put(new JSONObject()
                        .put("foo", "bar")
                        .put("value", 1337))
                .put(new JSONObject())
                .put(new JSONArray()
                        .put("Read this!")
                        .put(3.141))
                .put(new JSONArray())
                .put((Object) null);

        JsonElement result = converter.toJsonElement(jsonArray);

        assertThat(result, instanceOf(JsonArray.class));
        assertThat(((JsonArray) result), equalTo(JsonArrayBuilder.array()
                .add(true)
                .add(false)
                .add(42)
                .add(-7)
                .add(0)
                .add(3112.17)
                .add(-0.33)
                .add(0.0)
                .add("My fake plants died because I did not pretend to water them. - Mitch Hedberg")
                .add("")
                .add(JsonObjectBuilder.object()
                        .put("foo", "bar")
                        .put("value", 1337))
                .add(JsonObjectBuilder.object())
                .add(JsonArrayBuilder.array()
                        .add("Read this!")
                        .add(3.141))
                .add(JsonArrayBuilder.array().build())
                .addNull()
                .build()));
    }
}