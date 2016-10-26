package com.apanda.base.Module.Gank.model


import com.apanda.base.Entity.GankBean
import com.apanda.base.Module.Gank.bean.DayBean
import com.apanda.base.Module.Gank.bean.HisBean
import com.apanda.base.Module.Gank.contract.GankContract
import com.apanda.base.base.baserx.RxSchedulers
import com.apanda.base.callback.JsonConvert
import com.lzy.okgo.OkGo
import com.lzy.okrx.RxAdapter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1

/**
 * des:
 * Created by xsf
 * on 2016.09.17:05
 */
class GankModel : GankContract.Model {


    override fun lodeGankBean(): Observable<GankBean> {
        return Observable.create(Observable.OnSubscribe<com.apanda.base.Entity.GankBean> { _subscriber ->
            OkGo.get("http://gank.io/api/data/福利/20/1")//
                    .getCall(object : JsonConvert<GankBean>() {

                    }, RxAdapter.create<GankBean>())//以上为产生请求事件,请求默认发生在IO线程
                    .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                    .subscribe(Action1<com.apanda.base.Entity.GankBean> { s ->
                        _subscriber.onNext(s)
                        _subscriber.onCompleted()
                    }, Action1<kotlin.Throwable> { throwable ->
                        throwable.printStackTrace()
                        //handleError(null, null);
                    })
        }).compose(RxSchedulers.io_main<GankBean>())
    }

    override fun loadHistory(): Observable<HisBean> {
        return Observable.create(Observable.OnSubscribe<com.apanda.base.Module.Gank.bean.HisBean> { _subscriber ->
            OkGo.get("http://gank.io/api/day/history")//
                    .getCall(object : JsonConvert<HisBean>() {

                    }, RxAdapter.create<HisBean>())//以上为产生请求事件,请求默认发生在IO线程
                    .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                    .subscribe(Action1<com.apanda.base.Module.Gank.bean.HisBean> { s ->
                        _subscriber.onNext(s)
                        _subscriber.onCompleted()
                    }, Action1<kotlin.Throwable> { throwable ->
                        throwable.printStackTrace()
                        //handleError(null, null);
                    })
        }).compose(RxSchedulers.io_main<HisBean>())

    }

    override fun loadDayBean(_history: String): Observable<DayBean> {
        return Observable.create(Observable.OnSubscribe<com.apanda.base.Module.Gank.bean.DayBean> { _subscriber ->
            OkGo.get("http://gank.io/api/day/" + _history)//
                    .getCall(object : JsonConvert<DayBean>() {

                    }, RxAdapter.create<DayBean>())//以上为产生请求事件,请求默认发生在IO线程
                    .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                    .subscribe(Action1<com.apanda.base.Module.Gank.bean.DayBean> { s ->
                        _subscriber.onNext(s)
                        _subscriber.onCompleted()
                    }, Action1<kotlin.Throwable> { throwable ->
                        throwable.printStackTrace()
                        //handleError(null, null);
                    })
        }).compose(RxSchedulers.io_main<DayBean>())
    }
}
