package de.crunc.hamcrest.json.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
 * Unit test for {@link VertxJsonArrayToGsonConverter}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class VertxJsonArrayToGsonConverterTest {

    private VertxJsonArrayToGsonConverter converter;

    @Before
    public void setUp() {
        converter = new VertxJsonArrayToGsonConverter();
    }

    @Test
    public void shouldConvertEmptyArray() {
        io.vertx.core.json.JsonArray vertxArray = VertxJsonArrayBuilder.array()
                .build();

        JsonElement result = converter.toJsonElement(vertxArray);

        assertThat(result, instanceOf(JsonArray.class));
        assertThat(((JsonArray) result), equalTo(new JsonArray()));
    }

    @Test
    public void shouldConvertArray() {
        io.vertx.core.json.JsonArray vertxArray = VertxJsonArrayBuilder.array()
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
                .add(VertxJsonObjectBuilder.object()
                        .put("foo", "bar")
                        .put("value", 1337))
                .add(VertxJsonObjectBuilder.object())
                .add(VertxJsonArrayBuilder.array()
                        .add("Read this!")
                        .add(3.141))
                .addNull()
                .build();

        JsonElement result = converter.toJsonElement(vertxArray);

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
                .addNull()
                .build()));
    }
}
