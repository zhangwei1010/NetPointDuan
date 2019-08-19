package com.net.point.utils.stream;


import androidx.annotation.NonNull;

/**
 * Created by cxm
 * on 17-12-19.
 */

@SuppressWarnings("ALL")
public interface IfNullableConditionOptional<T> extends SuperOptional<T> {

    /**
     * 当条件成立时执行该方法
     *
     * @param consumer 函数
     * @return {@code NonNullOptional}
     */
    @NonNull
    public NullableOptional<T> apply(@NonNull Consumer<? super T> consumer);
}
