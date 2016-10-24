package com.apanda.base.Module.Gank.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.apanda.base.Module.Gank.adapter.GirlsAdapter;
import com.apanda.base.Module.Gank.bean.GilrsBean;
import com.apanda.base.Module.Gank.contract.FragmentGirlsContract;
import com.apanda.base.Module.Gank.model.FragmentGirlsModel;
import com.apanda.base.Module.Gank.presenter.FragmentGirlsPresenter;
import com.apanda.base.R;
import com.apanda.base.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public final class FragmentGirls extends BaseFragment<FragmentGirlsPresenter, FragmentGirlsModel> implements FragmentGirlsContract.View {


    @Bind(R.id.rcyview)
    RecyclerView _Rcyview;

    @Override
    protected int getLayoutResource() {
        return R.layout.content_activity_fragment_girlslayout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mPresenter.loadGrilsBean();
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showErrorTip(msg);
    }

    @Override
    public void returnGrilsBean(GilrsBean _gankBean) {
        GirlsAdapter adapter = new GirlsAdapter(getActivity(), _gankBean.results);
        _Rcyview.setAdapter(adapter);
        _Rcyview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}


