package de.crunc.hamcrest.json.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;


public class GsonUtils {
    public static JsonElement jsonToGson(Object value) {
        JsonElement element;

        if (value == null) {
            element = JsonNull.INSTANCE;
        } else if (value instanceof io.vertx.core.json.JsonObject) {
            element = VertxJsonObjectToGsonConverter.INSTANCE.toJsonElement((io.vertx.core.json.JsonObject) value);
        } else if (value instanceof io.vertx.core.json.JsonArray) {
            element = VertxJsonArrayToGsonConverter.INSTANCE.toJsonElement((io.vertx.core.json.JsonArray) value);
        } else if (value instanceof org.json.JSONObject) {
            element = JSONObjectToGsonConverter.INSTANCE.toJsonElement((org.json.JSONObject) value);
        } else if (value instanceof org.json.JSONArray) {
            element = JSONArrayToGsonConverter.INSTANCE.toJsonElement((org.json.JSONArray) value);
        } else if (value instanceof Boolean) {
            element = new JsonPrimitive((Boolean) value);
        } else if (value instanceof Number) {
            element = new JsonPrimitive((Number) value);
        } else if (value instanceof String) {
            element = new JsonPrimitive((String) value);
        }
        else {
            throw new GsonConversionException("can not convert class " +value.getClass().getCanonicalName() + " <" + value + "> to a <JsonElement>");
        }
        return element;
    }
}
