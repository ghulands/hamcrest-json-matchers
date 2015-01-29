package de.crunc.hamcrest.json.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts JSON values to {@link JsonElement} using {@link ToGsonConverter}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.1
 */
public enum GsonConverter {

    /**
     * Singleton instance.
     */
    INSTANCE();

    private final Map<Class<?>, ToGsonConverter<?>> converters;

    private GsonConverter() {
        converters = new HashMap<Class<?>, ToGsonConverter<?>>();

        add(new StringToGsonConverter());

        add(new VertxJsonObjectToGsonConverter());
        add(new VertxJsonArrayToGsonConverter());
        
        add(new JSONObjectToGsonConverter());
        add(new JSONArrayToGsonConverter());
    }

    private <T> void add(ToGsonConverter<T> converter) {
        converters.put(converter.sourceType(), converter);
    }

    /**
     * Converts the given JSON to an equal {@link JsonElement}. If the given JSON is {@code null} it is being converted
     * to {@link JsonNull}.
     *
     * @param json The JSON that will be converted.
     * @param <T>  The type of the json.
     * @return The {@link JsonElement} that equals the given JSON or {@link JsonNull}.
     * @throws GsonConversionException If the given JSON can not be converted.
     * @since 0.1
     */
    public <T> JsonElement toJsonElement(T json) {
        if (json == null) {
            return JsonNull.INSTANCE;
        }

        if (json instanceof JsonElement) {
            return (JsonElement) json;
        }

        ToGsonConverter<?> converter = converters.get(json.getClass());

        if (converter == null) {
            throw new GsonConversionException("can not convert <" + json + "> to <JsonElement>," +
                    "there is no ToGsonConverter registered for type <" + json.getClass() + ">");
        }

        @SuppressWarnings("unchecked")
        ToGsonConverter<T> typedConverter = (ToGsonConverter<T>) converter;
        return typedConverter.toJsonElement(json);
    }

    /**
     * Static version of {@link #toJsonElement(Object)}.
     *
     * @see #toJsonElement(Object)
     * @since 0.1
     */
    public static <T> JsonElement convert(T json) {
        return INSTANCE.toJsonElement(json);
    }
}
