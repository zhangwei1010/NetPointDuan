package com.net.point.utils.stream;


import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.List;

/**
 * 定义一系列操作符。
 *
 * @author cxm
 * @since 5.3.2
 */

public final class Operator {

    /**
     * 检查当前对象是否为空
     *
     * @param <T>   数据类型
     * @param value 需要被检查的对象
     * @return 如果当前对象为空则返回 {@code true} 否则返回 {@code false}
     */
    @Contract(value = "null -> true; !null -> false", pure = true)
    public static <T> boolean nullable(@Nullable T value) {
        return null == value;
    }

    /**
     * 检查当前对象是否不为空
     *
     * @param <T>   数据类型
     * @param value 需要被检查的对象
     * @return 如果当前对象为空则返回 {@code false} 否则返回 {@code true}
     */
    @Contract(value = "null -> false; !null -> true", pure = true)
    public static <T> boolean notNull(@Nullable T value) {
        return null != value;
    }

    /**
     * 如果当前传入的集合引用为 {@code null}、或者为 {@code empty} 则返回 {@code false}。
     *
     * @param <T>        数据类型
     * @param collection 需要被检查是否为 {@code empty} 的引用。
     * @return 如果当前传入的集合引用为 {@code null}、或者为 {@code empty} 则返回 {@code false}，否则返回 {@code true}。
     */
    @Contract("null -> false")
    public static <T> boolean isNotEmpty(@Nullable Collection<T> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * 默认函数。如果当前 {@code list} 对象不为空，则返回该列表的第一个元素。否则返回 {@code null}。
     *
     * @param list 一个可以为空的列表引用
     * @return 如果当前 {@code list} 对象不为空，则返回该列表的第一个元素。否则返回 {@code null}。
     */
    @Nullable
    @Contract("null -> null")
    public static <T> T firstOrNull(@Nullable List<T> list) {
        return isNotEmpty(list) ? list.get(0) : null;
    }

    /**
     * 默认函数。如果当前 {@code list} 对象不为空，则返回该列表的最后一个元素。否则返回 {@code null}。
     *
     * @param list 一个可以为空的列表引用
     * @return 如果当前 {@code list} 对象不为空，则返回该列表的最后一个元素。否则返回 {@code null}。
     */
    @Nullable
    @Contract("null -> null")
    public static <E> E lastOrNull(@Nullable List<E> list) {
        return isNotEmpty(list) ? list.get(list.size() - 1) : null;
    }
}
