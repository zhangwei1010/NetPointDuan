package com.net.point.base

import androidx.annotation.CallSuper
import com.net.point.request.HttpResult
import com.net.point.request.RetrofitUtil
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import java.io.File
import java.lang.ref.SoftReference
import java.util.*
import java.util.function.Supplier
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by cxm
 * on 2017/5/20.
 */

open class BaseMvpModel {
    private val subscriptions = LinkedList<Subscription>()

    fun Subscription.register() {
        subscriptions.add(this)
    }
//
//    class ServiceReference<out T>(creator: Supplier<T>) {
//        val service: T by softReference(creator::get)
//    }

    fun <T> Observable<HttpResult<T>>.result(onError: Action1<Throwable>, onNext: Action1<T>) =
            result(onError::call, onNext::call)

    fun <T> Observable<HttpResult<T>>.result(onError: (Throwable) -> Unit, onNext: (T) -> Unit) =
            register(onError) { it.data?.apply(onNext) }

    fun <T> Observable<T>.register(onError: Action1<Throwable>, onNext: Action1<T>) =
            register(onError::call, onNext::call)

    fun <T> Observable<T>.register(onError: (Throwable) -> Unit, onNext: (T) -> Unit) =
            this.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        try {
                            onNext(it)
                        } catch (e: Throwable) {
                            onError(e)
                        }
                    }, onError)
                    .register()

    fun <T> Observable<T>.register() {
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(RetrofitUtil.getDefault())
                .register()
    }

    fun String.createRequestBody(): RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this)

    fun File.createRequestBody(field: String = "uploadfile"): MultipartBody.Part = MultipartBody.Part.createFormData(field, this.name, RequestBody.create(MediaType.parse("multipart/form-data"), this))

    @CallSuper
    open fun onDestroy() {
        subscriptions.filterNot { it.isUnsubscribed }
                .forEach { it.unsubscribe() }
        subscriptions.clear()
    }

    /**
     * Kotlin代码使用,创建一个只读的软引用对象
     */
    fun <T> softReference(init: () -> T): ReadOnlyProperty<Any, T> =
            object : ReadOnlyProperty<Any, T> {
                private var softValue: SoftReference<T>? = null
                override fun getValue(thisRef: Any, property: KProperty<*>): T =
                        softValue?.get() ?: init().apply { softValue = SoftReference(this) }
            }

}
