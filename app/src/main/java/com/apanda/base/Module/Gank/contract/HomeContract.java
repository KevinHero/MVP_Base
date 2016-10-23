package com.apanda.base.Module.Gank.contract;


import com.apanda.base.base.BaseModel;
import com.apanda.base.base.BasePresenter;
import com.apanda.base.base.BaseView;

/**
 * Created by apanda on 2016/10/21.
 */

public class HomeContract {
    public interface Model extends BaseModel {

    }

    public interface View extends BaseView {

    }

    public abstract static class Presenter extends BasePresenter<View, Model> {

    }

}
