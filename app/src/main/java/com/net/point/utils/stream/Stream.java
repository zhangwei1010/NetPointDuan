package com.net.point.utils.stream;


import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Comparator;
import java.util.List;

/**
 * 序列操作
 * <p>
 * Created by cxm
 * on 17-12-27.
 */

@SuppressWarnings("unused")
public interface Stream<T> {

    /**
     * 判断是否所有的元素都满足函数条件
     *
     * @param predicate 条件函数
     * @return 当前所有元素是否全部满足条件
     * @throws NullPointerException 如果{@code predicate} 为null
     */
    @CheckResult
    boolean all(@NonNull Predicate<T> predicate);

    /**
     * 对所有元素依次应用该函数，该操作不产生额外的结果。
     *
     * @param consumer 需要对元素操作的函数
     * @return 返回原 {@code Stream} 对象
     * @throws NullPointerException 如果{@code consumer} 为null
     */
    @NonNull
    @CheckResult
    Stream<T> apply(@NonNull Consumer<T> consumer);

    /**
     * 判断当前序列中是否包含该元素
     *
     * @param element 需要判断的元素对象
     * @return 当前序列中是否包含该元素
     * @throws NullPointerException 如果{@code element} 为null
     */
    @CheckResult
    boolean contains(@NonNull T element);

    /**
     * 获得当前序列中的元素的个数
     */
    @CheckResult
    int count();

    /**
     * 计算当前序列中满足条件的元素的个数
     *
     * @param predicate 条件判断函数
     * @return 满足条件的元素的个数
     */
    @CheckResult
    int count(@NonNull Predicate<T> predicate);

    /**
     * 当序列不存在任何元素时，调用一个生产函数产生一个新的元素序列
     *
     * @param supplier 创建函数
     * @return 新数据序列
     * @throws NullPointerException 如果{@code supplier} 为null
     */
    @NonNull
    @CheckResult
    Stream<T> defaultEmpty(@NonNull Supplier<Stream<T>> supplier);

    /**
     * 从数据序列中过滤重复项
     */
    @NonNull
    @CheckResult
    Stream<T> distinct();

    /**
     * 按照指定分类条件过滤重复项。这个函数根据原始{@link Stream}的数据项产生一个Key，然后，比较这些Key。
     * 如果两个数据的key相同，则只保留最先到达的数据。
     *
     * @param function 过滤条件
     * @param <R>      key的数据类型
     * @return 过滤重复项之后的元素序列
     */
    @NonNull
    @CheckResult
    <R> Stream<T> distinct(@NonNull Function<T, R> function);

    /**
     * 判断当前序列中是否包含满足该条件的元素
     *
     * @param predicate 判断条件函数
     * @return 当前序列中是否包含满足条件的元素
     * @throws NullPointerException 如果{@code predicate} 为null
     */
    @CheckResult
    boolean exists(@NonNull Predicate<T> predicate);

    /**
     * 设置过滤器，只向下传递满足条件的数据
     *
     * @param predicate 过滤条件
     * @return 过滤之后的元素序列
     * @throws NullPointerException 如果{@code predicate} 为null
     */
    @NonNull
    @CheckResult
    Stream<T> filter(@NonNull Predicate<T> predicate);

    /**
     * 获得当前序列中的第一个元素
     *
     * @return 第一个元素，如果没有合适的元素则返回null
     */
    @NonNull
    @CheckResult
    NullableOptional<T> first();

    /**
     * 获得当前序列中第一个满足条件的元素
     *
     * @param predicate 条件表达式
     * @return 第一个元素，如果没有合适的元素则返回null
     */
    @NonNull
    @CheckResult
    NullableOptional<T> first(@NonNull Predicate<T> predicate);

    /**
     * 对数据进行变换，并将产生新的{@link Stream} 对象
     *
     * @param function 变换函数
     * @param <R>      目标类型
     * @return 重新序列化的{@link Stream} 对象
     * @throws NullPointerException 如果{@code function} 为null
     */
    @NonNull
    @CheckResult
    <R> Stream<R> flatMap(@NonNull Function<T, Stream<R>> function);

    /**
     * 对所有元素依次应用该函数，该操作不产生额外的结果。
     *
     * @param consumer 需要对元素操作的函数
     * @throws NullPointerException 如果{@code consumer} 为null
     */
    void forEach(@NonNull Consumer<T> consumer);

    /**
     * 判断当前数据序列是否不包含任何数据
     */
    @CheckResult
    boolean isEmpty();

    /**
     * 获得当前序列中的最后一个元素
     *
     * @return 最后一个元素，如果没有合适的元素则返回null
     */
    @NonNull
    @CheckResult
    NullableOptional<T> last();

    /**
     * 获得当前序列中最后一个满足条件的元素
     *
     * @param predicate 条件表达式
     * @return 最后一个元素，如果没有合适的元素则返回null
     */
    @NonNull
    @CheckResult
    NullableOptional<T> last(@NonNull Predicate<T> predicate);

    /**
     * 对数据进行变换，并将新产生的序列重新序列化为{@link Stream} 对象
     *
     * @param function 变换函数
     * @param <R>      目标类型
     * @return 重新序列化的{@link Stream} 对象
     * @throws NullPointerException 如果{@code function} 为null
     */
    @NonNull
    @CheckResult
    <R> Stream<R> map(@NonNull Function<T, R> function);

    /**
     * 获取序列中的最大值
     *
     * @param operator 比较操作符
     * @return 序列中的最大值
     */
    @NonNull
    @CheckResult
    NullableOptional<T> max(@NonNull Comparator<T> operator);

    /**
     * 获取序列中的最小值
     *
     * @param operator 比较操作符
     * @return 序列中的最小值
     */
    @NonNull
    @CheckResult
    NullableOptional<T> min(@NonNull Comparator<T> operator);

    /**
     * 过滤操作符，仅当数据类型为指定类型时数据才会进到下一步操作。
     *
     * @param type 指定数据类型
     * @param <R>  目标类型
     * @return 返回所有满足条件的数据序列
     * @throws NullPointerException 如果{@code type} 为null
     */
    @NonNull
    @CheckResult
    <R> Stream<R> ofType(@NonNull Class<R> type);

    /**
     * 按顺序扫描元素，并依次对数据执行 {@code function} 函数，其中得到的结果将会被运用于下一轮运算。
     *
     * @param function 变换函数
     * @return 运算结果序列化的{@link Stream} 对象
     * @throws NullPointerException 如果{@code function} 为null
     */
    @NonNull
    @CheckResult
    Stream<T> scan(@NonNull Function2<T, T, T> function);

    /**
     * 按顺序扫描元素，并依次对数据执行 {@code function} 函数，其中得到的结果将会被运用于下一轮运算。
     *
     * @param seed     该元素作为函数运算的种子，仅参与运算，但不会增加数据元素
     * @param function 变换函数
     * @return 运算结果序列化的{@link Stream} 对象
     * @throws NullPointerException 如果{@code function} 为null
     */
    @NonNull
    @CheckResult
    Stream<T> scan(@Nullable T seed, @NonNull Function2<T, T, T> function);

    /**
     * 比较两个序列的全部元素，当两个序列包含完全相同的元素并且顺序相同，返回true
     *
     * @param stream 待比较的序列
     * @return 两个序列是否相同
     */
    @CheckResult
    boolean sequenceEqual(@NonNull Stream<T> stream);

    /**
     * 自定义比较规则比较两个序列的元素是否相同
     *
     * @param stream     待比较的序列
     * @param comparator 自定义规则
     * @return 两个序列是否相同
     */
    @CheckResult
    boolean sequenceEqual(@NonNull Stream<T> stream, @NonNull Comparator<T> comparator);

    /**
     * 跳过之前的指定数量的元素
     *
     * @param count 需要跳过的元素个数
     * @return 去除指定个数元素后的序列
     * @throws IllegalArgumentException 如果 count 小于 0
     */
    @NonNull
    @CheckResult
    Stream<T> skip(int count);

    /**
     * 跳过之前的满足条件的元素，然后返回从第一个不满足条件的位置至结束的新的数据序列
     *
     * @param predicate 判断条件
     * @return 第一个不满足条件的位置至结束的新的数据序列
     * @throws NullPointerException 如果{@code predicate} 为null
     */
    @NonNull
    @CheckResult
    Stream<T> skipWhile(@NonNull Predicate<T> predicate);

    /**
     * 获得当前序列的和元素
     *
     * @param function 计算和的函数
     * @throws NullPointerException 如果{@code function} 为null
     */
    @NonNull
    @CheckResult
    NullableOptional<T> sum(@NonNull Function2<T, T, T> function);

    /**
     * 取当前数据序列的指定个数个元素组成新的数据序列
     *
     * @param count 指定元素个数
     * @return 指定个数元素后的序列
     * @throws IllegalArgumentException 如果 count 小于 0
     */
    @NonNull
    @CheckResult
    Stream<T> take(int count);

    /**
     * 获取当前数据序列的元素组成新的序列，直到条件不满足
     *
     * @param predicate 判断条件
     * @return 直到不满足条件的数据的序列
     * @throws NullPointerException 如果{@code predicate} 为null
     */
    @NonNull
    @CheckResult
    Stream<T> takeWhile(@NonNull Predicate<T> predicate);

    /**
     * 将当前数据流重新序列化为链表
     *
     * @return 返回对象不为null
     */
    @NonNull
    @CheckResult
    List<T> toList();

    /**
     * 将当前数据流重新序列化为数组
     *
     * @return 返回对象不为null
     */
    @NonNull
    @CheckResult
    Object[] toArray();

    /**
     * 将当前数据流重新序列化为指定类型数据的数组
     *
     * @param function 该函数传入一个当前序列的数量，需要调用者根据数量创建一个用于承载数据的空数组。
     * @return 可能为空的数组选项
     */
    @NonNull
    @CheckResult
    NullableOptional<T[]> toArray(@NonNull Function<Integer, T[]> function);
}
