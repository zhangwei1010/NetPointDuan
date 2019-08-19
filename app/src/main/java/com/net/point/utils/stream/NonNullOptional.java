package com.net.point.utils.stream;


import androidx.annotation.NonNull;

/**
 * Created by cxm
 * on 17-12-19.
 */

@SuppressWarnings("ALL")
public interface NonNullOptional<T> extends SuperOptional<T> {

    /**
     * 如果“值”可用，则对该“值”应用该函数。
     * 该函数不需要返回值。运算完成之后返回当前正在使用的 {@code NonNullOptional}.
     *
     * @param consumer 如果“值”可用，则对该“值”应用该函数。
     * @return 运算完成之后返回当前正在使用的 {@code NonNullOptional}.
     */
    @NonNull
    public NonNullOptional<T> apply(@NonNull Consumer<? super T> consumer);

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
     * 如果存在{@code value}，则将提供的带有{@code NonNullOptional}的映射函数应用于该函数，
     * 返回该结果，否则返回一个空的{@code NonNullOptional}。
     * 此方法与{@link #map（Function）}相似，但所提供的映射器的结果已经是一个{NonNullOptional}。
     *
     * @param <U>    返回的{@code NonNullOptional}的类型参数
     * @param mapper 如果存在映射函数，则将映射函数应用于该值
     * @throws NullPointerException 如果{@code mapper}为空
     */
    @NonNull
    public <U> NonNullOptional<U> flatMapNonNull(@NonNull Function<? super T, NonNullOptional<U>> mapper);

    /**
     * 获得当前持有的对象，无论通过{@link #of(Object)} 还是 {@link #ofNullable(Object)}，经过变换后无法保证值的状态
     */
    @NonNull
    public T get();

    /**
     * 如果条件成立，则会执行 {@link IfNonNullConditionOptional#apply(Predicate)} 方法
     *
     * @param predicate 执行判断条件
     * @return 条件表达式
     */
    @NonNull
    public IfNonNullConditionOptional<T> ifCondition(@NonNull Predicate<? super T> predicate);

    /**
     * 如果存在值，则使用该值调用指定的消费者，否则不执行任何操作。
     *
     * @param consumer 如果存在值，则执行该函数
     * @throws NullPointerException 如果{@code consumer}为空
     */
    public void ifPresent(@NonNull Consumer<? super T> consumer);

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
}
