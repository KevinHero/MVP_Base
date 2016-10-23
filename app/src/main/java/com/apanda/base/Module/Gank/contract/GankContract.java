package com.apanda.base.Module.Gank.contract;


import com.apanda.base.Entity.GankBean;
import com.apanda.base.Module.Gank.bean.DayBean;
import com.apanda.base.Module.Gank.bean.HisBean;
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

        Observable<HisBean> loadHistory();

        Observable<DayBean> loadDayBean(String _history);
    }

    public interface View extends BaseView {
        void returnGankBean(GankBean _gankBean);

        void returnHistory(String History);

        void returnDayBean(DayBean _dayBean);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeGankBeanRequest();

        public abstract void loadDayBean(String _history);
    }

}
