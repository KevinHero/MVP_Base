package com.apanda.base.Module.Gank.activity;

import android.os.Message;
import android.widget.ListView;

import com.apanda.base.Entity.GankBean;
import com.apanda.base.Module.Gank.adapter.GankAdapter;
import com.apanda.base.Module.Gank.contract.GankContract;
import com.apanda.base.Module.Gank.model.GankModel;
import com.apanda.base.Module.Gank.presenter.GankPresenter;
import com.apanda.base.R;
import com.apanda.base.base.BaseActivity;

import butterknife.Bind;

public class GankActivity extends BaseActivity<GankPresenter, GankModel> implements GankContract.View {


    @Bind(R.id.lv_items)
    ListView _LvItems;

    @Override
    public void returnGankBean(GankBean _gankBean) {
        _LvItems.setAdapter(new GankAdapter(_gankBean));
    }


    @Override
    protected void refresh(Message msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gank;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mPresenter.lodeGankBeanRequest();
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog(title);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showLongToast(msg);
    }


}
