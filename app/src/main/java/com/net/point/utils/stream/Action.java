package com.net.point.utils.stream;

/**
 * 定义一个行为函数。
 * <p>
 * 该函数不执行指定参数的操作，也不需要返回结果。
 * <p>
 * 这是一个函数式接口。{@link #call()}是其操作函数。
 *
 * @author cxm
 * @since 5.3.2
 */
@FunctionalInterface
public interface Action {

    /**
     * 执行操作的函数。
     */
    void call();
}