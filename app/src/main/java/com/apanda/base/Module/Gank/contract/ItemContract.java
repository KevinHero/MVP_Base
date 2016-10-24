package com.apanda.base.Module.Gank.contract;


import com.apanda.base.Module.Gank.bean.ItemsBean;
import com.apanda.base.base.BaseModel;
import com.apanda.base.base.BasePresenter;
import com.apanda.base.base.BaseView;

import rx.Observable;

public class ItemContract {

    public interface Model extends BaseModel {
        Observable<ItemsBean> loadCatoBean(String _type);
    }

    public interface View extends BaseView {
        void returnItemsBean(ItemsBean _gankBean);
    }

    public abstract static class Presenter extends BasePresenter<ItemContract.View, ItemContract.Model> {
    }

}
