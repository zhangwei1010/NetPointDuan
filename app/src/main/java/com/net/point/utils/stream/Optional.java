package com.net.point.utils.stream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 容器对象，可能包含或不包含非空值。
 *
 * @author cxm
 * @since 5.3.2
 */
@RequiresApi(api = 19)
public final class Optional {
    /**
     * 构造一个空的实例。
     */
    private Optional() {
    }

    /**
     * 用存在的值构造一个{@code Optional}实例。
     *
     * @param <T>   值的类型
     * @param value 非空值
     * @return 一个{@code Optional}实例
     * @throws NullPointerException 如果值为null
     */
    @NonNull
    public static <T> NonNullOptional<T> of(@NonNull T value) {
        return SimpleOptional.of(value);
    }

    /**
     * 用存在的值构造一个{@code Optional}实例。
     *
     * @param <T>      值的类型
     * @param supplier 值的产生函数
     * @return 一个{@code Optional}实例
     * @throws NullPointerException 如果值为null
     */
    @NonNull
    public static <T> NonNullOptional<T> of(@NonNull Supplier<T> supplier) {
        return SimpleOptional.of(supplier);
    }

    /**
     * 用可能存在的值构造一个{@code Optional}实例。
     *
     * @param <T>   值的类型
     * @param value 可能为空的值
     * @return 一个{@code Optional}实例，如果值为空返回 {@code #empty()}
     */
    @NonNull
    public static <T> NullableOptional<T> ofNullable(@Nullable T value) {
        return SimpleOptional.ofNullable(value);
    }

    /**
     * 用可能存在的值构造一个{@code Optional}实例。
     *
     * @param <T>      值的类型
     * @param supplier 值的产生函数
     * @return 一个{@code Optional}实例，如果值为空返回 {@code #empty()}
     */
    @NonNull
    public static <T> NullableOptional<T> ofNullable(@NonNull Supplier<T> supplier) {
        return SimpleOptional.ofNullable(supplier);
    }
}