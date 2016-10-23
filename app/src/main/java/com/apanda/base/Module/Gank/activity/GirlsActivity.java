package com.apanda.base.Module.Gank.activity;

import com.apanda.base.Module.Gank.contract.GirlsContract;
import com.apanda.base.Module.Gank.model.GirlsModel;
import com.apanda.base.Module.Gank.presenter.GirlsPresenter;
import com.apanda.base.R;
import com.apanda.base.base.BaseActivity;


public final class GirlsActivity extends BaseActivity<GirlsPresenter, GirlsModel> implements GirlsContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.activity_girls;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}


