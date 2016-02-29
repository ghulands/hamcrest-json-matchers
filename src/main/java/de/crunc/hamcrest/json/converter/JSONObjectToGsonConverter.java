package de.crunc.hamcrest.json.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Converts {@link JSONObject} to {@link JsonElement}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.2
 */
public class JSONObjectToGsonConverter implements ToGsonConverter<org.json.JSONObject> {

    /**
     * @since 0.2
     */
    static final JSONObjectToGsonConverter INSTANCE = new JSONObjectToGsonConverter();
    
    @Override
    public Class<org.json.JSONObject> sourceType() {
        return org.json.JSONObject.class;
    }

    @Override
    public JsonElement toJsonElement(org.json.JSONObject source) {

        JsonObject gsonObject = new JsonObject();

        for (String name : source.keySet()) {
            Object value = source.get(name);
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

            gsonObject.add(name, element);
        }
        
        return gsonObject;
    }
}
