package com.net.point.utils.stream;

/**
 * 表示接受一个参数并产生结果的函数。
 * <p>
 * 这是一个函数式接口。{@link #apply(int)}是其操作函数。
 *
 * @author cxm
 * @since 5.3.2
 */
@FunctionalInterface
public interface IntToIntFunction {

    /**
     * 将此函数应用于给定的参数。
     *
     * @param t 函数参数
     * @return 函数结果
     */
    int apply(int t);
}
