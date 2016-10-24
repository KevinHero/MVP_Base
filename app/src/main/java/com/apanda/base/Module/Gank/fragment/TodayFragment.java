package com.apanda.base.Module.Gank.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.apanda.base.Entity.GankBean;
import com.apanda.base.Module.Gank.adapter.ListViewMainAdapter;
import com.apanda.base.Module.Gank.adapter.ViewPagerAdapter;
import com.apanda.base.Module.Gank.bean.DayBean;
import com.apanda.base.Module.Gank.contract.GankContract;
import com.apanda.base.Module.Gank.model.GankModel;
import com.apanda.base.Module.Gank.presenter.GankPresenter;
import com.apanda.base.R;
import com.apanda.base.Utils.logger.L;
import com.apanda.base.Widget.NestedListView;
import com.apanda.base.base.BaseFragment;

import butterknife.Bind;

public class TodayFragment extends BaseFragment<GankPresenter, GankModel> implements GankContract.View {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(R.id.vp_title)
    ViewPager _VpTitle;
    @Bind(R.id.lv_main)
    NestedListView _LvMain;

    private String mParam1;
    private String mParam2;


    public TodayFragment() {
    }


    public static TodayFragment newInstance(String param1, String param2) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.content_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        startProgressDialog();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mPresenter.lodeGankBeanRequest();
        mPresenter.loadHistoty();
    }


    @Override
    public void returnGankBean(GankBean _gankBean) {
        initViewPager(_gankBean);
    }

    private void initViewPager(GankBean bitmapBeans) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), bitmapBeans.results);
        _VpTitle.setAdapter(viewPagerAdapter);
        _VpTitle.setCurrentItem(0);
    }

    @Override
    public void returnHistory(String History) {
        L.json(History);
        mPresenter.loadDayBean(History);
    }

    @Override
    public void returnDayBean(DayBean _dayBean) {
        _LvMain.setAdapter(new ListViewMainAdapter(getContext(), _dayBean, false));
        stopProgressDialog();
    }

    @Override
    public void showLoading(String title) {
        showLoading(title);
    }

    @Override
    public void stopLoading() {
        stopLoading();
    }

    @Override
    public void showErrorTip(String msg) {

    }
}
