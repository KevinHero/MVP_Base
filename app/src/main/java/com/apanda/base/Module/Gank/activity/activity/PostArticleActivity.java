package com.apanda.base.Module.Gank.activity.activity;

import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.apanda.base.Module.Gank.activity.contract.PostArticleContract;
import com.apanda.base.Module.Gank.activity.model.PostArticleModel;
import com.apanda.base.Module.Gank.activity.presenter.PostArticlePresenter;
import com.apanda.base.R;
import com.apanda.base.base.BaseActivity;

import butterknife.Bind;


public final class PostArticleActivity extends BaseActivity<PostArticlePresenter, PostArticleModel> implements PostArticleContract.View {

    @Bind(R.id.toolbar)
    Toolbar _Toolbar;
    @Bind(R.id.btn_post)
    Button _BtnPost;

    @Override
    public int getLayoutId() {
        return R.layout.activity_post_article;
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


