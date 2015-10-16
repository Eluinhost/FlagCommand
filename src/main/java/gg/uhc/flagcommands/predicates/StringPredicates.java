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
