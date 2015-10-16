package gg.uhc.flagcommands.converters;

import joptsimple.ValueConversionException;


public class IntegerConverter extends PredicatedConverter<Integer> {

    @Override
    public Integer convert(String value) {
        try {
            int i = Integer.parseInt(value);

            if (!isValid(i)) {
                throw new ValueConversionException("Invalid integer supplied, expected '" + getType().or("integer") + "' found: " + value);
            }

            return i;
        } catch (NumberFormatException e) {
            throw new ValueConversionException("Invalid number: " + value, e);
        }
    }

    @Override
    public Class<Integer> valueType() {
        return Integer.class;
    }

    @Override
    public String valuePattern() {
        return getType().or("integer");
    }
}
