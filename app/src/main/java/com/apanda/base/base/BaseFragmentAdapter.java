package com.apanda.base.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<String> mTitles;
    private String[] _title;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mTitles = mTitles;
    }

    public BaseFragmentAdapter(FragmentManager fm, String[] _title) {
        super(fm);
        this._title = _title;
    }

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> _fragments, String[] _title) {

        super(fm);
        this.fragmentList = _fragments;
        this._title = _title;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _title[position];
//        return !CollectionUtils.isNullOrEmpty(mTitles) ? mTitles.get(position) : "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
