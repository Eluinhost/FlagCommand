package gg.uhc.flagcommands.converters;

import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;

public class EnumConverter <T extends Enum> implements ValueConverter<T> {

    protected final Class<T> type;

    protected EnumConverter(Class<T> type) {
        this.type = type;
    }

    @Override
    public T convert(String value) {
        try {
            return (T) Enum.valueOf(type, value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ValueConversionException("Unknown " + type.getSimpleName() + ": " + value, ex);
        }
    }

    @Override
    public Class<T> valueType() {
        return type;
    }

    @Override
    public String valuePattern() {
        return type.getSimpleName();
    }

    /**
     * Creates a new EnumConverter for the given enum class
     *
     * @param klass the enum to provide a converter for
     * @param <B> the enum class
     *
     * @return the created converter
     */
    public static <B extends Enum> EnumConverter<B> forEnum(Class<B>  klass) {
        return new EnumConverter<>(klass);
    }
}
