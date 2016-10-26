package com.apanda.base.Module.Gank.model


import com.apanda.base.Module.Gank.bean.GilrsBean
import com.apanda.base.Module.Gank.contract.FragmentGirlsContract
import com.apanda.base.base.baserx.RxSchedulers
import com.apanda.base.callback.JsonConvert
import com.lzy.okgo.OkGo
import com.lzy.okrx.RxAdapter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1


class FragmentGirlsModel : FragmentGirlsContract.Model {

    override fun loadGrilsBean(): Observable<GilrsBean> {


        return Observable.create(Observable.OnSubscribe<com.apanda.base.Module.Gank.bean.GilrsBean> { _subscriber ->
            OkGo.get("http://gank.io/api/data/福利/20/1")//
                    .getCall(object : JsonConvert<GilrsBean>() {

                    }, RxAdapter.create<GilrsBean>())//以上为产生请求事件,请求默认发生在IO线程
                    .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                    .subscribe(Action1<com.apanda.base.Module.Gank.bean.GilrsBean> { s ->
                        _subscriber.onNext(s)
                        _subscriber.onCompleted()
                    }, Action1<kotlin.Throwable> { throwable -> throwable.printStackTrace() })
        }).compose(RxSchedulers.io_main<GilrsBean>())
    }
}
