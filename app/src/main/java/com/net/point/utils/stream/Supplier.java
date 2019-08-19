package com.net.point.utils.stream;

/**
 * 代表结果的提供函数。
 * <p>
 * 这里没有要求在每次调用的时候返回新的或唯一的结果。
 * <p>
 * 这是一个函数式接口。 {@link #get()}是其操作函数。
 *
 * @param <T> 该函数提供的结果类型
 * @author cxm
 * @since 5.3.2
 */
@FunctionalInterface
public interface Supplier<T> {

    /**
     * 获取结果。
     *
     * @return 结果
     */
    T get();
}