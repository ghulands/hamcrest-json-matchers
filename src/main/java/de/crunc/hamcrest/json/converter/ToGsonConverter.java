package de.crunc.hamcrest.json.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import javax.annotation.Nullable;

/**
 * Converts a JSON object/array/string to a GSON {@link JsonElement}
 *
 * @param <T> The type of the object that can be converted.
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.1
 */
public interface ToGsonConverter<T> {

    /**
     * The type of the source that can be handled by this converter.
     *
     * @return The type that can be handled.
     * @since 0.1
     */
    Class<T> sourceType();

    /**
     * Converts the given source to a {@link JsonElement}.
     *
     * @param source The JSON source.
     * @return A JsonElement equal to the given source.
     * @since 0.1
     */
    JsonElement toJsonElement(T source);
}
