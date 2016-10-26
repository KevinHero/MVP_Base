package com.apanda.base.base.baserx


import com.apanda.base.Utils.logger.L
import com.apanda.base.base.basebean.BaseRespose

import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers


/**
 * des:对服务器返回数据成功和失败处理
 * Created by xsf
 * on 2016.09.9:59
 */
/**************使用例子 */
/*_apiService.login(mobile, verifyCode)
        .compose(RxSchedulersHelper.io_main())
        .compose(RxResultHelper.handleResult())
        .//省略*/

object RxHelper {
    /**
     * 对服务器返回数据进行预处理

     * @param
     * *
     * @return
     */
    fun <T> handleResult(): Observable.Transformer<BaseRespose<T>, T> {
        return Observable.Transformer<com.apanda.base.base.basebean.BaseRespose<T>, T> { tObservable ->
            tObservable.flatMap { result ->
                L.d("result from api : " + result)
                if (result.success()) {
                    createData(result.data)
                } else {
                    Observable.error<T>(ServerException(result.msg))
                }
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }

    }

    /**
     * 创建成功的数据

     * @param data
     * *
     * @param
     * *
     * @return
     */
    private fun <T> createData(data: T): Observable<T> {
        return Observable.create { subscriber ->
            try {
                subscriber.onNext(data)
                subscriber.onCompleted()
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }

    }
}
