package gg.uhc.flagcommands.converters;

import joptsimple.ValueConversionException;

/**
 * Converts String arguments into doubles.
 */
public class DoubleConverter extends PredicatedConverter<Double> {

    @Override
    public Double convert(String value) {
        try {
            double i = Double.parseDouble(value);

            if (!isValid(i)) {
                throw new ValueConversionException("Invalid number supplied, expected '" + type.or("double") + "' found: " + value);
            }

            // otherwise success
            return i;
        } catch (NumberFormatException e) {
            // catch invalid numbers
            throw new ValueConversionException("Invalid number: " + value, e);
        }
    }

    @Override
    public Class<Double> valueType() {
        return Double.class;
    }

    @Override
    public String valuePattern() {
        return getType().or("double");
    }
}
