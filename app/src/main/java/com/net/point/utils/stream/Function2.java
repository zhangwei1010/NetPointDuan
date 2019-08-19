package com.net.point.utils.stream;

/**
 * 表示接受一个参数并产生结果的函数。
 * <p>
 * 这是一个函数式接口。{@link #apply(Object, Object)}是其操作函数。
 *
 * @param <T1> 该函数的输入类型
 * @param <T2> 该函数的输入类型
 * @param <R>  该函数的输出类型
 * @author cxm
 * @since 5.3.2
 */
@FunctionalInterface
public interface Function2<T1, T2, R> {

    /**
     * 将此函数应用于给定的参数。
     *
     * @param t1 函数参数
     * @param t2 函数参数
     * @return 函数结果
     */
    R apply(T1 t1, T2 t2);
}