/*
 * Project: FlagCommands
 * Class: gg.uhc.flagcommands.converters.EnumConverter
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Graham Howden <graham_howden1 at yahoo.co.uk>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
