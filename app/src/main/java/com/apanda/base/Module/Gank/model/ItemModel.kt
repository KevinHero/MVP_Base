package com.apanda.base.Module.Gank.model


import com.apanda.base.Module.Gank.bean.ItemsBean
import com.apanda.base.Module.Gank.contract.ItemContract
import com.apanda.base.base.baserx.RxSchedulers
import com.apanda.base.callback.JsonConvert
import com.lzy.okgo.OkGo
import com.lzy.okrx.RxAdapter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1


class ItemModel : ItemContract.Model {

    override fun loadCatoBean(_type: String): Observable<ItemsBean> {
        return Observable.create(Observable.OnSubscribe<com.apanda.base.Module.Gank.bean.ItemsBean> { _subscriber ->
            OkGo.get("http://gank.io/api/data/$_type/20/1")//
                    .getCall(object : JsonConvert<ItemsBean>() {

                    }, RxAdapter.create<ItemsBean>())//以上为产生请求事件,请求默认发生在IO线程
                    .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                    .subscribe(Action1<com.apanda.base.Module.Gank.bean.ItemsBean> { s ->
                        _subscriber.onNext(s)
                        _subscriber.onCompleted()
                    }, Action1<kotlin.Throwable> { throwable -> throwable.printStackTrace() })
        }).compose(RxSchedulers.io_main<ItemsBean>())
    }
}
