package com.apanda.base.Module.Gank.model;


import com.apanda.base.Module.Gank.bean.ItemsBean;
import com.apanda.base.Module.Gank.contract.ItemContract;
import com.apanda.base.base.baserx.RxSchedulers;
import com.apanda.base.callback.JsonConvert;
import com.lzy.okgo.OkGo;
import com.lzy.okrx.RxAdapter;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class ItemModel implements ItemContract.Model {

    @Override
    public Observable<ItemsBean> loadCatoBean(final String _type) {
        return Observable.create(new Observable.OnSubscribe<ItemsBean>() {
            @Override
            public void call(final Subscriber<? super ItemsBean> _subscriber) {
                OkGo.get("http://gank.io/api/data/" + _type + "/20/1")//
                        .getCall(new JsonConvert<ItemsBean>() {
                        }, RxAdapter.<ItemsBean>create())//以上为产生请求事件,请求默认发生在IO线程
                        .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                        .subscribe(new Action1<ItemsBean>() {
                            @Override
                            public void call(ItemsBean s) {
                                _subscriber.onNext(s);
                                _subscriber.onCompleted();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });

            }
        }).compose(RxSchedulers.<ItemsBean>io_main());
    }
}
