package com.net.point.utils.stream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

/**
 * Created by cxm
 * on 17-12-19.
 */

@SuppressWarnings("ALL")
public interface NullableOptional<T> extends SuperOptional<T> {

    /**
     * 如果“值”可用，则对该“值”应用该函数。
     * 该函数不需要返回值。运算完成之后返回当前正在使用的 {@code NullableOptional}.
     *
     * @param consumer 如果“值”可用，则对该“值”应用该函数。
     * @return 运算完成之后返回当前正在使用的 {@code NullableOptional}.
     */
    @NonNull
    public NullableOptional<T> apply(@NonNull Consumer<? super T> consumer);

    /**
     * 如果存在一个值，并且该值匹配给定的条件,返回 {@code NullableOptional}, 否则返回 {@code #empty()}
     *
     * @param predicate 值匹配给定的条件
     * @return 条件成立返回当前{@code NullableOptional}实例，否则返回 {@code #empty()}
     * @throws NullPointerException 如果{@code predicate}为空
     */
    @NonNull
    public NullableOptional<T> filter(@NonNull Predicate<? super T> predicate);

    /**
     * 如果存在一个值，并且该值不匹配给定的条件,返回 {@code NullableOptional}, 否则返回 {@code #empty()}
     *
     * @param predicate 值匹配给定的条件
     * @return 条件不成立返回当前{@code NullableOptional}实例，否则返回 {@code #empty()}
     * @throws NullPointerException 如果{@code predicate}为空
     */
    @NonNull
    public NullableOptional<T> filterNot(@NonNull Predicate<? super T> predicate);

    /**
     * 如果存在{@code value}，则将提供的带有{@code NullableOptional}的映射函数应用于该函数，
     * 返回该结果，否则返回一个空的{@code NullableOptional}。
     * 此方法与{@link #map（Function）}相似，但所提供的映射器的结果已经是一个{NullableOptional}。
     *
     * @param <U>    返回的{@code NullableOptional}的类型参数
     * @param mapper 如果存在映射函数，则将映射函数应用于该值
     * @throws NullPointerException 如果{@code mapper}为空
     */
    @NonNull
    public <U> NullableOptional<U> flatMap(@NonNull Function<? super T, SuperOptional<U>> mapper);

    /**
     * 如果不存在{@code value}，则将提供的带有{@code NullableOptional}的映射函数应用于该函数，
     * 返回该结果，否则返回一个空的{@code NullableOptional}。
     * 此方法与{@link #mapNotPresent(Supplier)} 相似，但所提供的映射器的结果已经是一个{NullableOptional}。
     *
     * @param supplier 如果存在映射函数，则将映射函数应用于该值
     * @throws NullPointerException 如果{@code mapper}为空
     */
    @NonNull
    public NullableOptional<T> flatMapNotPresenter(@NonNull Supplier<SuperOptional<T>> supplier);

    /**
     * 获得当前持有的对象，无论通过{@link #of(Object)} 还是 {@link #ofNullable(Object)}，经过变换后无法保证值的状态
     */
    @Nullable
    public T get();

    /**
     * 如果条件成立，则会执行 {@link IfNonNullConditionOptional#apply(Predicate)} 方法
     *
     * @param predicate 执行判断条件
     * @return 条件表达式
     */
    @NonNull
    public IfNullableConditionOptional<T> ifCondition(@NonNull Predicate<? super T> predicate);

    /**
     * 如果不存在值，则调用一个行为函数。
     *
     * @param action 如果不存在值，则执行该函数
     * @throws NullPointerException 如果{@code action}为空
     */
    public void ifNotPresent(@NonNull Action action);

    /**
     * 如果存在值，则使用该值调用指定的消费者，否则不执行任何操作。
     *
     * @param consumer 如果存在值，则执行该函数
     * @throws NullPointerException 如果{@code consumer}为空
     */
    public void ifPresent(@NonNull Consumer<? super T> consumer);

    /**
     * 获得当前持有的对象。
     *
     * @throws NullPointerException 如果当前持有对象为空
     */
    @NonNull
    public T getPresent();

    /**
     * 如果存在值，则使用该值调用指定的消费者，否则不执行任何操作。
     *
     * @param consumer 如果存在值，则执行该函数
     * @throws RuntimeException 该方法拦截一切异常并重新以 {@link RuntimeException} 抛出
     */
    public void ifPresentThrows(@NonNull ConsumerThrows<? super T> consumer);

    /**
     * 如果存在值，则应用提供的映射函数，如果结果不为空，则返回描述结果的{@code NullableOptional}。
     * 否则返回一个空{@code NullableOptional}。
     *
     * @param <U>    映射函数结果的类型
     * @param mapper 如果存在，则应用该值的映射函数
     * @throws NullPointerException 如果{@code mapper}为空
     */
    @NonNull
    public <U> NullableOptional<U> map(@NonNull Function<? super T, ? extends U> mapper);

    /**
     * 如果不存在值，则应用提供的映射函数，如果结果不为空，则返回描述结果的{@code NullableOptional}。
     * 否则返回一个空{@code NullableOptional}。
     *
     * @param supplier 如果不存在，则应用该值的映射函数
     * @throws NullPointerException 如果{@code supplier}为空
     */
    @NonNull
    public NullableOptional<T> mapNotPresent(@NonNull Supplier<T> supplier);

    /**
     * 返回值如果存在，否则返回{@code other}。
     *
     * @param other 如果不存在值，则返回的值可能为空
     * @return 如果值存在返回该值，否则{@code other}
     */
    @Contract("!null -> !null")
    @Nullable
    public T orElse(@Nullable T other);

    /**
     * Return the value if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * @param other a {@code Supplier} whose result is returned if no value
     *              is present
     * @return the value if present otherwise the result of {@code other.get()}
     * @throws NullPointerException if value is not present and {@code other} is
     *                              null
     */
    @Nullable
    public T orElseGet(@NonNull Supplier<? extends T> other);

    /**
     * Return the contained value, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * @param <X>               Type of the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to
     *                          be thrown
     * @return the present value
     * @throws X                    if there is no value present
     * @throws NullPointerException if no value is present and
     *                              {@code exceptionSupplier} is null
     * @apiNote A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     */
    public <X extends Throwable> T orElseThrow(@NonNull Supplier<? extends X> exceptionSupplier) throws X;
}
