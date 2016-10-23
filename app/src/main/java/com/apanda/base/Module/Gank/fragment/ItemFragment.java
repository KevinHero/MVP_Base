package com.apanda.base.Module.Gank.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.apanda.base.R;
import com.apanda.base.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by apanda on 2016/10/23.
 */

public class ItemFragment extends BaseFragment {
    @Bind(R.id.tv)
    TextView _Tv;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_today;
    }

    @Override
    public void initPresenter() {

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

        _Tv.setText("fragment" + mParam1);
    }


}
