package com.net.point.utils.stream;

/**
 * 处理{@code bool}值的操作函数
 *
 * @author cxm
 * @since 5.3.2
 */
@FunctionalInterface
public interface BoolConsumer {

    /**
     * 对给定的参数执行此操作。
     *
     * @param t 输入参数
     */
    void accept(boolean t);
}