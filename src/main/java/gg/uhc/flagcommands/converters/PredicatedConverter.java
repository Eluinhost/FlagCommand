package gg.uhc.flagcommands.converters;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import joptsimple.ValueConverter;

public abstract class PredicatedConverter <T> implements ValueConverter<T> {
    protected Optional<Predicate<T>> predicate = Optional.absent();
    protected Optional<String> type = Optional.absent();

    /**
     * Set the predicate determining valid results. If the predicate returns false then a ValueConversion exception is
     * thrown when parsing. If not provided or null then no checks are done on the parsed double and any double is allowed
     *
     * @param predicate the predicate to used
     * @return this for chaining
     */
    public PredicatedConverter<T> setPredicate(Predicate<T> predicate) {
        this.predicate = Optional.fromNullable(predicate);
        return this;
    }

    /**
     * Set the 'type' of this converter. This is shown in command help screens as the arg type and in an error message if
     * the predicate returns false. If not provided/null then the class name will be used instead.
     *
     * @param type the string to show for the type
     * @return this for chaining
     */
    public PredicatedConverter<T> setType(String type) {
        this.type = Optional.fromNullable(type);
        return this;
    }

    public Optional<Predicate<T>> getPredicate() {
        return predicate;
    }

    public Optional<String> getType() {
        return type;
    }

    public boolean isValid(T value) {
        return !predicate.isPresent() || predicate.get().apply(value);
    }
}
