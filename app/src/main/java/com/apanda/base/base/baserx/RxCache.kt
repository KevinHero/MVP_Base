package com.apanda.base.base.baserx

import android.content.Context

import com.apanda.base.Utils.ACache

import java.io.Serializable

import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers

/**
 * des:处理服务器数据的缓存
 * Created by xsf
 * on 2016.09.11:01
 */


object RxCache {
    /**

     * @param context
     * *
     * @param cacheKey
     * *
     * @param expireTime
     * *
     * @param fromNetwork
     * *
     * @param forceRefresh
     * *
     * @param
     * *
     * @return
     */
    fun <T> load(context: Context,
                 cacheKey: String,
                 expireTime: Int,
                 fromNetwork: Observable<T>,
                 forceRefresh: Boolean): Observable<T> {
        var fromNetwork = fromNetwork
        val fromCache = Observable.create(Observable.OnSubscribe<T> { subscriber ->
            //获取缓存
            val cache = ACache.get(context).getAsObject(cacheKey) as T
            if (cache != null) {
                subscriber.onNext(cache)
            } else {
                subscriber.onCompleted()
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        /**
         * 这里的fromNetwork 不需要指定Schedule,在handleRequest中已经变换了
         */
        fromNetwork = fromNetwork.map { result ->
            //保存缓存
            ACache.get(context).put(cacheKey, result as Serializable, expireTime)
            result
        }
        //强制刷新则返回接口数据
        if (forceRefresh) {
            return fromNetwork
        } else {
            //优先返回缓存
            return Observable.concat(fromCache, fromNetwork).first()
        }
    }
}
