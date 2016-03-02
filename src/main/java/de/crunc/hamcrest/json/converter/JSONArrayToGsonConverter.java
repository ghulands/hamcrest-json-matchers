package de.crunc.hamcrest.json.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import org.json.JSONArray;

/**
 * Converts {@link JSONArray} to {@link JsonElement}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.2
 */
public class JSONArrayToGsonConverter implements ToGsonConverter<org.json.JSONArray> {

    /**
     * @since 0.2
     */
    static final JSONArrayToGsonConverter INSTANCE = new JSONArrayToGsonConverter();
    
    @Override
    public Class<org.json.JSONArray> sourceType() {
        return org.json.JSONArray.class;
    }

    @Override
    public JsonElement toJsonElement(org.json.JSONArray source) {

        JsonArray gsonArray = new JsonArray();

        for (int index = 0; index < source.length(); ++index) {
            Object value = source.opt(index);
            final JsonElement element;

            if (value == null) {
                element = JsonNull.INSTANCE;
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
            } else {
                throw new GsonConversionException("can not convert <" + value + "> to a <JsonElement>");
            }

            gsonArray.add(element);
        }

        return gsonArray;
    }
}
