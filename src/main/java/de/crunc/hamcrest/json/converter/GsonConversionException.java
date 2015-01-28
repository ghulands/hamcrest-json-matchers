package de.crunc.hamcrest.json.converter;

/**
 * Thrown when converting something to a GSON value fails.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 0.1
 */
public class GsonConversionException extends RuntimeException {

    public GsonConversionException() {
        super();
    }

    public GsonConversionException(String message) {
        super(message);
    }

    public GsonConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public GsonConversionException(Throwable cause) {
        super(cause);
    }

    public GsonConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
