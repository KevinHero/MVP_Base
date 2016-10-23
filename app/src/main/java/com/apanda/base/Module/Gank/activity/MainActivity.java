package com.apanda.base.Module.Gank.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.apanda.base.Entity.GankBean;
import com.apanda.base.Module.Gank.contract.GankContract;
import com.apanda.base.Module.Gank.adapter.ListViewMainAdapter;
import com.apanda.base.Module.Gank.adapter.ViewPagerAdapter;
import com.apanda.base.Module.Gank.bean.DayBean;
import com.apanda.base.Module.Gank.model.GankModel;
import com.apanda.base.Module.Gank.presenter.GankPresenter;
import com.apanda.base.R;
import com.apanda.base.Utils.logger.L;
import com.apanda.base.base.BaseActivity;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import butterknife.Bind;

public class MainActivity extends BaseActivity<GankPresenter, GankModel> implements GankContract.View
        , NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar _Toolbar;
    @Bind(R.id.vp_title)
    ViewPager _VpTitle;
    @Bind(R.id.lv_main)
    ListView _LvMain;
    @Bind(R.id.boom)
    BoomMenuButton _Boom;
    @Bind(R.id.nav_view)
    NavigationView _NavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout _DrawerLayout;


    @Override
    protected void refresh(Message msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {

       // mPresenter.lodeGankBeanRequest();
        initToolBar(_Toolbar, "每日精选妹纸", R.color.red_btn_bg_color);
        initBoomButton();
        initDrawer();
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, _DrawerLayout, _Toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        _DrawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        _NavView.setNavigationItemSelectedListener(this);
    }

    private void initBoomButton() {
        _Boom.setCancelable(true);
        _Boom.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                switch (buttonIndex) {
                    case 0:
                        startActivity(HuabanGirlActivity.class);
                        break;
                    case 1:
                        startActivity(HistoryTodayActivity.class);
                        break;
                    case 2:
                        startActivity(BeautyActivity.class);
                        break;
                }
            }
        });
    }

    private boolean init = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


        if (init) return;
        init = true;

        Drawable[] subButtonDrawables = new Drawable[3];
        int[] drawablesResource = new int[]{
                R.mipmap.huagirl,
                R.mipmap.histotry,
                R.mipmap.beauty

        };
        for (int i = 0; i < 3; i++)
            subButtonDrawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

        String[] subButtonTexts = new String[]{"BoomMenuButton", "View source code", "Follow me"};

        int[][] subButtonColors = new int[3][2];
        for (int i = 0; i < 3; i++) {
            subButtonColors[i][1] = ContextCompat.getColor(this, R.color.colorPrimary);
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);
        }

        _Boom.init(
                subButtonDrawables, // The drawables of images of sub buttons. Can not be null.
                null,     // The texts of sub buttons, ok to be null.
                subButtonColors,    // The colors of sub buttons, including pressed-state and normal-state.
                ButtonType.CIRCLE,     // The button type.
//                ButtonType.HAM,     // The button type.
                BoomType.PARABOLA,  // The boom type.
                PlaceType.CIRCLE_3_3,  // The place type.
                null,               // Ease type to move the sub buttons when showing.
                null,               // Ease type to scale the sub buttons when showing.
                null,               // Ease type to rotate the sub buttons when showing.
                null,               // Ease type to move the sub buttons when dismissing.
                null,               // Ease type to scale the sub buttons when dismissing.
                null,               // Ease type to rotate the sub buttons when dismissing.
                null                // Rotation degree.
        );

        _Boom.setTextViewColor(ContextCompat.getColor(this, R.color.black));

    }


    private void initViewPager(GankBean bitmapBeans) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, bitmapBeans.results);
        _VpTitle.setAdapter(viewPagerAdapter);
        _VpTitle.setCurrentItem(0);
        stopProgressDialog();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            HistoryListActivity.startActivity(MainActivity.this, "福利", "休息视频", 20, 1);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.one) {
//            setIntent("Android");
            HistoryListActivity.startActivity(MainActivity.this, "福利", "Android", 20, 0);
        } else if (id == R.id.two) {
//            setIntent("IOS");
            HistoryListActivity.startActivity(MainActivity.this, "福利", "IOS", 20, 0);
        } else if (id == R.id.three) {
//            setIntent("休息视频");
            HistoryListActivity.startActivity(MainActivity.this, "福利", "休息视频", 20, 0);
        } else if (id == R.id.four) {
//            setIntent("福利");
            HistoryListActivity.startActivity(MainActivity.this, "福利", "福利", 20, 0);
        } else if (id == R.id.five) {
//            setIntent("拓展资源");
            HistoryListActivity.startActivity(MainActivity.this, "福利", "拓展资源", 20, 0);
        } else if (id == R.id.six) {
//            setIntent("前端");
            HistoryListActivity.startActivity(MainActivity.this, "福利", "前端", 20, 0);
        } else if (id == R.id.seven) {
//            setIntent("App");
            HistoryListActivity.startActivity(MainActivity.this, "福利", "App", 20, 0);
        } else if (id == R.id.eight) {
//            setIntent("瞎推荐");
            HistoryListActivity.startActivity(MainActivity.this, "福利", "瞎推荐", 20, 0);
        } else if (id == R.id.nine) {
//            setIntent("随便看看");
//            HistoryListActivity.startActivity(MainActivity.this, "福利", "随便看看", 20, 0);
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void returnGankBean(GankBean _gankBean) {
        initViewPager(_gankBean);
    }

    @Override
    public void returnHistory(String History) {
        L.json(History);
        mPresenter.loadDayBean(History);
    }

    @Override
    public void returnDayBean(DayBean _dayBean) {
        _LvMain.setAdapter(new ListViewMainAdapter(this, _dayBean, false));
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
