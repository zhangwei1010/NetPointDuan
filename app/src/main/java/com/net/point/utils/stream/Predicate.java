package com.net.point.utils.stream;

/**
 * 表示一个参数的谓词（布尔值函数）。
 * <p>
 * 这是一个函数式接口。{@link #test(Object)}是其操作函数。
 *
 * @author cxm
 * @since 5.3.2
 */
@FunctionalInterface
public interface Predicate<T> {

    /**
     * 在给定的参数上评估这个谓词。
     *
     * @param t 输入参数
     * @return 如果输入参数匹配谓词返回 {@code true},否则返回 {@code false}
     */
    boolean test(T t);
}