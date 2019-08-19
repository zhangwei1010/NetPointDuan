@file:Suppress("unused")

package com.muqi.app.util.stream

import com.net.point.utils.stream.NonNullOptional
import com.net.point.utils.stream.NullableOptional
import com.net.point.utils.stream.Optional

/**
 * 用存在的值构造一个{@code Optional}实例。
 *
 * @param <T>   值的类型
 * @param T 非空值
 * @return 一个{@code Optional}实例
 * @throws NullPointerException 如果值为null
 */
fun <T : Any> T.ofNotnull(): NonNullOptional<T> = Optional.of(this)

/**
 * 用可能存在的值构造一个{@code Optional}实例。
 *
 * @param <T>   值的类型
 * @param T 可能为空的值
 * @return 一个{@code Optional}实例，如果值为空返回 {@code #empty()}
 */
fun <T : Any> T?.ofNullable(): NullableOptional<T> = Optional.ofNullable(this)