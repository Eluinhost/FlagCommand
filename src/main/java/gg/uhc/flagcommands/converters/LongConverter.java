package gg.uhc.flagcommands.converters;

import joptsimple.ValueConversionException;

public class LongConverter extends PredicatedConverter<Long> {

    @Override
    public Long convert(String value) {
        try {
            long i = Long.parseLong(value);

            if (!isValid(i)) {
                throw new ValueConversionException("Invalid number supplied, expected '" + getType().or("long") + "' found: " + value);
            }

            return i;
        } catch (NumberFormatException e) {
            throw new ValueConversionException("Invalid number: " + value, e);
        }
    }

    @Override
    public Class<Long> valueType() {
        return Long.class;
    }

    @Override
    public String valuePattern() {
        return getType().or("long");
    }
}
