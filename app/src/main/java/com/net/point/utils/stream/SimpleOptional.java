package com.net.point.utils.stream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

/**
 * 容器对象，可能包含或不包含非空值。
 *
 * @author cxm
 * @since 5.3.2
 */
@RequiresApi(api = 19)
final class SimpleOptional<T> implements NullableOptional<T>, NonNullOptional<T>,
        IfNullableConditionOptional<T>, IfNonNullConditionOptional<T> {
    /**
     * {@code empty()} 示例.
     */
    @NonNull
    private static final SimpleOptional<?> EMPTY = new SimpleOptional<>();
    /**
     * 如果非null，则该值; 如果为空，则表示不存在任何值。
     */
    @Nullable
    private final T value;
    private boolean condition = true;

    /**
     * 构造一个空的实例。
     */
    private SimpleOptional() {
        this.value = null;
    }

    /**
     * 返回一个空的{@code Optional}实例。
     */
    @NonNull
    public static <T> SimpleOptional<T> empty() {
        @SuppressWarnings("unchecked")
        SimpleOptional<T> t = (SimpleOptional<T>) EMPTY;
        return t;
    }

    /**
     * 用存在的值构造一个实例。
     *
     * @param value 非空值
     * @throws NullPointerException 如果值为null
     */
    private SimpleOptional(@NonNull T value) {
        this.value = Objects.requireNonNull(value);
    }

    @NonNull
    public static <T> NonNullOptional<T> of(@NonNull T value) {
        return new SimpleOptional<>(value);
    }

    @NonNull
    public static <T> NonNullOptional<T> of(@NonNull Supplier<T> supplier) {
        return new SimpleOptional<>(Objects.requireNonNull(supplier).get());
    }

    @NonNull
    public static <T> NullableOptional<T> ofNullable(@Nullable T value) {
        //noinspection unchecked
        return value == null ? (SimpleOptional<T>) empty() : new SimpleOptional<>(value);
    }

    @NonNull
    public static <T> NullableOptional<T> ofNullable(@NonNull Supplier<T> supplier) {
        T value;
        //noinspection unchecked
        return (value = Objects.requireNonNull(supplier).get()) == null ? (SimpleOptional<T>) empty() : new SimpleOptional<>(value);
    }

    public void ifPresent(@NonNull Consumer<? super T> consumer) {
        if (value != null)
            Objects.requireNonNull(consumer).accept(value);
    }

    public void ifPresentThrows(@NonNull ConsumerThrows<? super T> consumer) {
        if (value != null)
            try {
                Objects.requireNonNull(consumer).accept(value);
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
    }

    public void ifNotPresent(@NonNull Action action) {
        if (value == null)
            Objects.requireNonNull(action).call();
    }

    @NonNull
    public SimpleOptional<T> filter(@NonNull Predicate<? super T> predicate) {
        if (value == null)
            return this;
        else
            return Objects.requireNonNull(predicate).test(value) ? this : empty();
    }

    @NonNull
    public SimpleOptional<T> filterNot(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (null == value)
            return this;
        else
            return Objects.requireNonNull(predicate).test(value) ? empty() : this;
    }

    @NonNull
    public <U> NullableOptional<U> map(@NonNull Function<? super T, ? extends U> mapper) {
        if (null == value)
            return empty();
        else {
            return SimpleOptional.ofNullable(Objects.requireNonNull(mapper).apply(value));
        }
    }

    @NonNull
    public NullableOptional<T> mapNotPresent(@NonNull Supplier<T> supplier) {
        if (null != value)
            return this;
        else {
            return SimpleOptional.ofNullable(Objects.requireNonNull(supplier).get());
        }
    }

    @NonNull
    public SimpleOptional<T> apply(@NonNull Consumer<? super T> consumer) {
        if (null == value)
            return empty();
        else {
            if (condition) {
                condition = true;
                Objects.requireNonNull(consumer).accept(value);
            }
            return this;
        }
    }

    @NonNull
    @Override
    public SimpleOptional<T> ifCondition(@NonNull Predicate<? super T> predicate) {
        if (value == null)
            return empty();
        else {
            condition = Objects.requireNonNull(predicate).test(value);
            return this;
        }
    }

    @NonNull
    public <U> SimpleOptional<U> flatMap(@NonNull Function<? super T, SuperOptional<U>> mapper) {
        if (null == value)
            return empty();
        else {
            return (SimpleOptional<U>) Objects.requireNonNull(Objects.requireNonNull(mapper).apply(value));
        }
    }

    @NonNull
    @Override
    public <U> NonNullOptional<U> flatMapNonNull(@NonNull Function<? super T, NonNullOptional<U>> mapper) {
        if (null == value)
            return empty();
        else {
            return Objects.requireNonNull(Objects.requireNonNull(mapper).apply(value));
        }
    }

    @NonNull
    public SimpleOptional<T> flatMapNotPresenter(@NonNull Supplier<SuperOptional<T>> supplier) {
        if (null != value)
            return this;
        else {
            return (SimpleOptional<T>) Objects.requireNonNull(supplier).get();
        }
    }

    @SuppressWarnings("NullableProblems")
    @Contract(pure = true)
    public T get() {
        return value;
    }

    @Contract(pure = true)
    @NonNull
    public T getPresent() {
        return Objects.requireNonNull(value);
    }

    @Contract("!null -> !null")
    @Nullable
    public T orElse(@Nullable T other) {
        return value != null ? value : other;
    }

    @Nullable
    public T orElseGet(@NonNull Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }

    public <X extends Throwable> T orElseThrow(@NonNull Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this Optional. The
     * other object is considered equal if:
     * <ul>
     * <li>it is also an {@code Optional} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code equals()}.
     * </ul>
     *
     * @param obj an object to be tested for equality
     * @return {code true} if the other object is "equal to" this object
     * otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SimpleOptional)) {
            return false;
        }

        SimpleOptional<?> other = (SimpleOptional<?>) obj;
        return Objects.equals(value, other.value);
    }

    /**
     * Returns the hash code value of the present value, if any, or 0 (zero) if
     * no value is present.
     *
     * @return hash code value of the present value or 0 if no value is present
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * Returns a non-empty string representation of this Optional suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     *
     * @return the string representation of this instance
     * @implSpec If a value is present the result must include its string
     * representation in the result. Empty and present Optionals must be
     * unambiguously differentiable.
     */
    @Override
    public String toString() {
        return value != null
                ? String.format("Optional[%s]", value)
                : "Optional.empty";
    }
}