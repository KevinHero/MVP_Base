package com.apanda.base.Module.Gank.model;


import com.apanda.base.Entity.GankBean;
import com.apanda.base.Module.Gank.contract.GankContract;
import com.apanda.base.Module.Gank.bean.DayBean;
import com.apanda.base.Module.Gank.bean.HisBean;
import com.apanda.base.base.baserx.RxSchedulers;
import com.apanda.base.callback.JsonConvert;
import com.lzy.okgo.OkGo;
import com.lzy.okrx.RxAdapter;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * des:
 * Created by xsf
 * on 2016.09.17:05
 */
public class GankModel implements GankContract.Model {


    @Override
    public Observable<GankBean> lodeGankBean() {
        return Observable.create(new Observable.OnSubscribe<GankBean>() {
            @Override
            public void call(final Subscriber<? super GankBean> _subscriber) {

                OkGo.get("http://gank.io/api/data/福利/20/1")//
                        .getCall(new JsonConvert<GankBean>() {
                        }, RxAdapter.<GankBean>create())//以上为产生请求事件,请求默认发生在IO线程
                        .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                        .subscribe(new Action1<GankBean>() {
                            @Override
                            public void call(GankBean s) {
                                _subscriber.onNext(s);
                                _subscriber.onCompleted();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                                //handleError(null, null);
                            }
                        });

            }
        }).compose(RxSchedulers.<GankBean>io_main());
    }

    @Override
    public Observable<HisBean> loadHistory() {
        return Observable.create(new Observable.OnSubscribe<HisBean>() {
            @Override
            public void call(final Subscriber<? super HisBean> _subscriber) {
                OkGo.get("http://gank.io/api/day/history")//
                        .getCall(new JsonConvert<HisBean>() {
                        }, RxAdapter.<HisBean>create())//以上为产生请求事件,请求默认发生在IO线程
                        .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                        .subscribe(new Action1<HisBean>() {
                            @Override
                            public void call(HisBean s) {
                                _subscriber.onNext(s);
                                _subscriber.onCompleted();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                                //handleError(null, null);
                            }
                        });

            }
        }).compose(RxSchedulers.<HisBean>io_main());

    }

    @Override
    public Observable<DayBean> loadDayBean(final String _history) {
        return Observable.create(new Observable.OnSubscribe<DayBean>() {
            @Override
            public void call(final Subscriber<? super DayBean> _subscriber) {
                OkGo.get("http://gank.io/api/day/" + _history)//
                        .getCall(new JsonConvert<DayBean>() {
                        }, RxAdapter.<DayBean>create())//以上为产生请求事件,请求默认发生在IO线程
                        .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                        .subscribe(new Action1<DayBean>() {
                            @Override
                            public void call(DayBean s) {
                                _subscriber.onNext(s);
                                _subscriber.onCompleted();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                                //handleError(null, null);
                            }
                        });

            }
        }).compose(RxSchedulers.<DayBean>io_main());
    }
}
