package de.crunc.hamcrest.json.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Converts {@link JSONObject} to {@link JsonElement}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.2
 */
public class JSONObjectToGsonConverter implements ToGsonConverter<JSONObject> {

    /**
     * @since 0.2
     */
    static final JSONObjectToGsonConverter INSTANCE = new JSONObjectToGsonConverter();
    
    @Override
    public Class<JSONObject> sourceType() {
        return JSONObject.class;
    }

    @Override
    public JsonElement toJsonElement(JSONObject source) {

        JsonObject gsonObject = new JsonObject();

        for (String name : source.keySet()) {
            Object value = source.get(name);
            final JsonElement element;

            if (value == null) {
                element = JsonNull.INSTANCE;
            } else if (value instanceof JSONObject) {
                element = JSONObjectToGsonConverter.INSTANCE.toJsonElement((JSONObject) value);
            } else if (value instanceof JSONArray) {
                element = JSONArrayToGsonConverter.INSTANCE.toJsonElement((JSONArray) value);
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
