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
        converters = new HashMap<>();

        add(new StringToGsonConverter());

        add("org.vertx.java.core.json.JsonObject", "de.crunc.hamcrest.json.converter.VertxJsonObjectToGsonConverter");
        add("org.vertx.java.core.json.JsonArray", "de.crunc.hamcrest.json.converter.VertxJsonArrayToGsonConverter");
        add("org.json.JSONObject", "de.crunc.hamcrest.json.converter.JSONObjectToGsonConverter");
        add("org.json.JSONArray", "de.crunc.hamcrest.json.converter.JSONArrayToGsonConverter");
    }

    /**
     * Instantiates and adds the given converter to the list of converters if the given target class is on the classpath.
     *
     * @param targetClassName The qualified name of the class that must be on the classpath in orer for the converter to be added, must not be {@code null}.
     * @param converterClassName The qualified name of the converter which will be added, must not be {@code null}.
     * @since 0.2
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void add(String targetClassName, String converterClassName) {
        try {
            Class.forName(targetClassName, false, this.getClass().getClassLoader());
            add((ToGsonConverter) Class.forName(converterClassName).newInstance());
        } catch (Exception e) {
        }
    }
    

    /**
     * Adds the given converter to the list of converters.
     *
     * @param converter The converter which will be added, must not be {@code null}.
     * @param <T>       The source type of the converter.
     * @since 0.1
     */
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
     * Converts the given JSON to an equal {@link JsonElement}. If the given JSON is {@code null} it is being converted
     * to {@link JsonNull}.
     *
     * @param json The JSON that will be converted.
     * @param <T>  The type of the json.
     * @return The {@link JsonElement} that equals the given JSON or {@link JsonNull}.
     * @throws GsonConversionException If the given JSON can not be converted.
     * @since 0.1
     */
    public static <T> JsonElement convert(T json) {
        return INSTANCE.toJsonElement(json);
    }
}
