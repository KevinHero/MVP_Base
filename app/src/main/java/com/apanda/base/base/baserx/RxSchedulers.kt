package com.apanda.base.base.baserx

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * RxJava调度管理
 * Created by xsf
 * on 2016.08.14:50
 */
object RxSchedulers {
    fun <T> io_main(): Observable.Transformer<T, T> {
        return Observable.Transformer<T, T> { observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }
}
