package com.apanda.base.Module.Gank.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.apanda.base.Module.Gank.adapter.ItemAdapter;
import com.apanda.base.Module.Gank.bean.ItemsBean;
import com.apanda.base.Module.Gank.contract.ItemContract;
import com.apanda.base.Module.Gank.model.ItemModel;
import com.apanda.base.Module.Gank.presenter.ItemPresenter;
import com.apanda.base.R;
import com.apanda.base.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apanda on 2016/10/23.
 */

public class ItemFragment extends BaseFragment<ItemPresenter, ItemModel> implements ItemContract.View {

    @Bind(R.id.rcyview)
    RecyclerView _Rcyview;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_today;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    private String mParam1;
    private String mParam2;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static ItemFragment newInstance(String param1, String param2) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initView() {
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mPresenter.loadBean(mParam1);
        _Rcyview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }


    @Override
    public void returnItemsBean(ItemsBean _gankBean) {
        _Rcyview.setAdapter(new ItemAdapter(getActivity(), _gankBean.results));
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
