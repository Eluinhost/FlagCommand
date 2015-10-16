package gg.uhc.flagcommands.converters;

import joptsimple.ValueConversionException;

public class StringConverter extends PredicatedConverter<String> {

    @Override
    public String convert(String s) {
        if (!isValid(s)) throw new ValueConversionException("Invalid value supplied. Expected " + getType().or("string") + " but found `" + s + "` instead.");

        return s;
    }

    @Override
    public Class<String> valueType() {
        return String.class;
    }


    @Override
    public String valuePattern() {
        return getType().or("string");
    }
}
