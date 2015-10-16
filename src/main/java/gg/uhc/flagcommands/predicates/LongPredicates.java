package gg.uhc.flagcommands.predicates;

import com.google.common.base.Predicate;

public class LongPredicates {
    public static final Predicate<Long> LESS_THAN_ZERO = new Predicate<Long>() {
        @Override
        public boolean apply(Long input) {
            return input < 0;
        }
    };

    public static final Predicate<Long> LESS_THAN_ZERO_INC = new Predicate<Long>() {
        @Override
        public boolean apply(Long input) {
            return input <= 0;
        }
    };

    public static final Predicate<Long> GREATER_THAN_ZERO = new Predicate<Long>() {
        @Override
        public boolean apply(Long input) {
            return input > 0;
        }
    };

    public static final Predicate<Long> GREATER_THAN_ZERO_INC = new Predicate<Long>() {
        @Override
        public boolean apply(Long input) {
            return input >= 0;
        }
    };
}
