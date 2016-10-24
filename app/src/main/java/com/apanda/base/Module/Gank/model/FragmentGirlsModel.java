package com.apanda.base.Module.Gank.model;


import com.apanda.base.Module.Gank.bean.GilrsBean;
import com.apanda.base.Module.Gank.contract.FragmentGirlsContract;
import com.apanda.base.base.baserx.RxSchedulers;
import com.apanda.base.callback.JsonConvert;
import com.lzy.okgo.OkGo;
import com.lzy.okrx.RxAdapter;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class FragmentGirlsModel implements FragmentGirlsContract.Model {

    @Override
    public Observable<GilrsBean> loadGrilsBean() {


        return Observable.create(new rx.Observable.OnSubscribe<GilrsBean>() {
            @Override
            public void call(final Subscriber<? super GilrsBean> _subscriber) {
                OkGo.get("http://gank.io/api/data/福利/20/1")//
                        .getCall(new JsonConvert<GilrsBean>() {
                        }, RxAdapter.<GilrsBean>create())//以上为产生请求事件,请求默认发生在IO线程
                        .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                        .subscribe(new Action1<GilrsBean>() {
                            @Override
                            public void call(GilrsBean s) {
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
        }).compose(RxSchedulers.<GilrsBean>io_main());
    }
}
