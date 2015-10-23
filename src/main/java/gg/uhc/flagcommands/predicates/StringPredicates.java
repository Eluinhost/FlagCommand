/*
 * Project: FlagCommands
 * Class: gg.uhc.flagcommands.predicates.StringPredicates
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

package gg.uhc.flagcommands.predicates;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

public class StringPredicates {

    protected abstract static class OfLength implements Predicate<String> {

        protected final int length;

        public OfLength(int length) {
            Preconditions.checkArgument(length > -1, "Must provide a length of zero or greater");

            this.length = length;
        }
    }

    public static class ExactLength extends OfLength {
        public ExactLength(int length) {
            super(length);
        }

        @Override
        public boolean apply(String input) {
            return input != null && input.length() == length;
        }
    }

    public static class LessThanLength extends OfLength {
        public LessThanLength(int length) {
            super(length);
        }

        @Override
        public boolean apply(String input) {
            return input != null && input.length() < length;
        }
    }

    public static class LessThanOrEqualLength extends OfLength {
        public LessThanOrEqualLength(int length) {
            super(length);
        }

        @Override
        public boolean apply(String input) {
            return input != null && input.length() <= length;
        }
    }

    public static class GreaterThanLength extends OfLength {
        public GreaterThanLength(int length) {
            super(length);
        }

        @Override
        public boolean apply(String input) {
            return input != null && input.length() > length;
        }
    }

    public static class GreaterThanOrEqualLength extends OfLength {
        public GreaterThanOrEqualLength(int length) {
            super(length);
        }

        @Override
        public boolean apply(String input) {
            return input != null && input.length() >= length;
        }
    }
}
