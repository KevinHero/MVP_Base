package com.apanda.base.base.baserx

import java.util.HashMap

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

/**
 * 用于管理单个presenter的RxBus的事件和Rxjava相关代码的生命周期处理
 * Created by xsf
 * on 2016.08.14:50
 */
class RxManager {
    var mRxBus = RxBus.getInstance()
    //管理rxbus订阅
    private val mObservables = HashMap<String, Observable<*>>()
    /*管理Observables 和 Subscribers订阅*/
    private val mCompositeSubscription = CompositeSubscription()

    /**
     * RxBus注入监听
     * @param eventName
     * *
     * @param action1
     */
    fun <T> on(eventName: String, action1: Action1<T>) {
        val mObservable = mRxBus.register<T>(eventName)
        mObservables.put(eventName, mObservable)
        /*订阅管理*/
        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(action1, Action1<kotlin.Throwable> { throwable -> throwable.printStackTrace() }))
    }

    /**
     * 单纯的Observables 和 Subscribers管理
     * @param m
     */
    fun add(m: Subscription) {
        /*订阅管理*/
        mCompositeSubscription.add(m)
    }

    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    fun clear() {
        mCompositeSubscription.unsubscribe()// 取消所有订阅
        for ((key, value) in mObservables) {
            mRxBus.unregister(key, value)// 移除rxbus观察
        }
    }

    //发送rxbus
    fun post(tag: Any, content: Any) {
        mRxBus.post(tag, content)
    }
}
