package gg.uhc.flagcommands.predicates;

import com.google.common.base.Predicate;

public class DoublePredicates {
    public static final Predicate<Double> LESS_THAN_ZERO = new Predicate<Double>() {
        @Override
        public boolean apply(Double input) {
            return input < 0;
        }
    };

    public static final Predicate<Double> LESS_THAN_ZERO_INC = new Predicate<Double>() {
        @Override
        public boolean apply(Double input) {
            return input <= 0;
        }
    };

    public static final Predicate<Double> GREATER_THAN_ZERO = new Predicate<Double>() {
        @Override
        public boolean apply(Double input) {
            return input > 0;
        }
    };

    public static final Predicate<Double> GREATER_THAN_ZERO_INC = new Predicate<Double>() {
        @Override
        public boolean apply(Double input) {
            return input >= 0;
        }
    };
}
