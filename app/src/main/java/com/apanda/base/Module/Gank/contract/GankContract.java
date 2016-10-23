package com.apanda.base.Module.Gank.contract;


import com.apanda.base.Entity.GankBean;
import com.apanda.base.base.BaseModel;
import com.apanda.base.base.BasePresenter;
import com.apanda.base.base.BaseView;

import rx.Observable;

/**
 * Created by apanda on 2016/10/21.
 */

public class GankContract {
    public interface Model extends BaseModel {
        Observable<GankBean> lodeGankBean();
    }

    public interface View extends BaseView {
        void returnGankBean(GankBean _gankBean);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeGankBeanRequest();
    }

}
