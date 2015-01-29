package de.crunc.hamcrest.json.matcher;

import org.junit.Test;

import static de.crunc.hamcrest.json.JsonMatchers.isJsonArray;
import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Sample tests for {@link IsJsonObject}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonObjectTestSamples {

    @Test
    public void testString() {

        String json = "{\"someObject\":{\"foo\":31},\"someArray\":[1,2,3]}";

        assertThat(json, isJsonObject()
                .prop("someObject", isJsonObject()
                        .prop("foo", 31))
                .prop("someArray", isJsonArray()
                        .item(1)
                        .item(2)
                        .item(3)));
    }

    @Test
    public void testGson() {

        com.google.gson.JsonObject someObject = new com.google.gson.JsonObject();
        someObject.add("foo", new com.google.gson.JsonPrimitive(31));

        com.google.gson.JsonArray someArray = new com.google.gson.JsonArray();
        someArray.add(new com.google.gson.JsonPrimitive(1));
        someArray.add(new com.google.gson.JsonPrimitive(2));
        someArray.add(new com.google.gson.JsonPrimitive(3));

        com.google.gson.JsonObject json = new com.google.gson.JsonObject();
        json.add("someObject", someObject);
        json.add("someArray", someArray);

        assertThat(json, isJsonObject()
                .prop("someObject", isJsonObject()
                        .prop("foo", 31))
                .prop("someArray", isJsonArray()
                        .item(1)
                        .item(2)
                        .item(3)));
    }
    
    @Test
    public void testVertx() {

        org.vertx.java.core.json.JsonObject someObject = new org.vertx.java.core.json.JsonObject();
        someObject.putNumber("foo", 31);

        org.vertx.java.core.json.JsonArray someArray = new org.vertx.java.core.json.JsonArray();
        someArray.addNumber(1);
        someArray.addNumber(2);
        someArray.addNumber(3);

        org.vertx.java.core.json.JsonObject json = new org.vertx.java.core.json.JsonObject();
        json.putObject("someObject", someObject);
        json.putArray("someArray", someArray);

        assertThat(json, isJsonObject()
                .prop("someObject", isJsonObject()
                        .prop("foo", 31))
                .prop("someArray", isJsonArray()
                        .item(1)
                        .item(2)
                        .item(3)));
    }

    @Test
    public void testJsonOrg() {

        org.json.JSONObject someObject = new org.json.JSONObject()
                .put("foo", 31);

        org.json.JSONArray someArray = new org.json.JSONArray();
        someArray.put(1);
        someArray.put(2);
        someArray.put(3);

        org.json.JSONObject json = new org.json.JSONObject()
                .put("someObject", someObject)
                .put("someArray", someArray);

        assertThat(json, isJsonObject()
                .prop("someObject", isJsonObject()
                        .prop("foo", 31))
                .prop("someArray", isJsonArray()
                        .item(1)
                        .item(2)
                        .item(3)));
    }
}
