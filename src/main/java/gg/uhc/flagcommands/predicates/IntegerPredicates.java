package gg.uhc.flagcommands.predicates;

import com.google.common.base.Predicate;

public class IntegerPredicates {
    public static final Predicate<Integer> LESS_THAN_ZERO = new Predicate<Integer>() {
        @Override
        public boolean apply(Integer input) {
            return input < 0;
        }
    };

    public static final Predicate<Integer> LESS_THAN_ZERO_INC = new Predicate<Integer>() {
        @Override
        public boolean apply(Integer input) {
            return input <= 0;
        }
    };

    public static final Predicate<Integer> GREATER_THAN_ZERO = new Predicate<Integer>() {
        @Override
        public boolean apply(Integer input) {
            return input > 0;
        }
    };

    public static final Predicate<Integer> GREATER_THAN_ZERO_INC = new Predicate<Integer>() {
        @Override
        public boolean apply(Integer input) {
            return input >= 0;
        }
    };
}
