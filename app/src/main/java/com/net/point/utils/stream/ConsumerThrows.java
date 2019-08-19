package com.net.point.utils.stream;

/**
 * 处理{@code T}类型值的操作函数
 *
 * @param <T> 需要被处理的类型泛型
 * @author cxm
 * @since 5.3.2
 */
@FunctionalInterface
public interface ConsumerThrows<T> {
    /**
     * 对给定的参数执行此操作。
     *
     * @param t 输入参数
     * @throws Throwable 该方法拦截一切异常并重新以 {@link RuntimeException} 抛出
     */
    void accept(T t) throws Throwable;
}