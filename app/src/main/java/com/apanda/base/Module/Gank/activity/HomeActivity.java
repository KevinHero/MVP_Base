package com.apanda.base.Module.Gank.activity;

import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.apanda.base.Module.Gank.fragment.ItemFragment;
import com.apanda.base.Module.Gank.fragment.TodayFragment;
import com.apanda.base.R;
import com.apanda.base.Utils.TabHostUtils;
import com.apanda.base.base.BaseActivity;
import com.apanda.base.base.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class HomeActivity extends BaseActivity {
//public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View, TodayFragment.OnFragmentInteractionListener {


    @Bind(R.id.toolbar)
    Toolbar _Toolbar;
    @Bind(R.id.tabs)
    TabLayout _Tabs;
    @Bind(R.id.view_pager)
    ViewPager _ViewPager;

    String title[] = {"今日", "安卓", "IOS", "福利", "随机", "视频"};

    @Override
    protected void refresh(Message msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gank;
    }

    @Override
    public void initPresenter() {

    }

    List<Fragment> _fragments = new ArrayList<>();

    @Override
    public void initView() {
        initToolBar(_Toolbar, "干活");
        _fragments.add(TodayFragment.newInstance("今日", ""));
        _fragments.add(ItemFragment.newInstance("Android", ""));
        _fragments.add(ItemFragment.newInstance("IOS", ""));
        _fragments.add(ItemFragment.newInstance("福利", ""));
        _fragments.add(ItemFragment.newInstance("随机", ""));
        _fragments.add(ItemFragment.newInstance("视频", ""));

        BaseFragmentAdapter fragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), _fragments, title);
        _ViewPager.setAdapter(fragmentAdapter);
        _Tabs.setupWithViewPager(_ViewPager);
        TabHostUtils.dynamicSetTabLayoutMode(_Tabs);
        setPageChangeListener();
    }

    private void setPageChangeListener() {
        _ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
