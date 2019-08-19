package com.net.point.base;

import java.util.LinkedList;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BaseModel {

    private List<Subscription> subscriptions = new LinkedList<>();

    public void register(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public <T> void register(Observable<T> observable, Action1<Throwable> onError, Action1<T> onNext) {
        Subscription subscribe = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> onNext.call(t), throwable -> onError.call(throwable));
        register(subscribe);
    }
}
