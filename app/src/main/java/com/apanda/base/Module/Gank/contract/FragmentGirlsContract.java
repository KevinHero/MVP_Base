package com.apanda.base.Module.Gank.contract;


import com.apanda.base.Module.Gank.bean.GilrsBean;
import com.apanda.base.base.BaseModel;
import com.apanda.base.base.BasePresenter;
import com.apanda.base.base.BaseView;

import rx.Observable;

public class FragmentGirlsContract {
    public interface Model extends BaseModel {
        Observable<GilrsBean> loadGrilsBean();
    }

    public interface View extends BaseView {

        void returnGrilsBean(GilrsBean _gankBean);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {

    }

}
