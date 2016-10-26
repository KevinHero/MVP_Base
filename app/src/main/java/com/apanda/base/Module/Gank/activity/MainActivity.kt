package com.apanda.base.Module.Gank.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Message
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import butterknife.Bind
import com.apanda.base.Entity.GankBean
import com.apanda.base.Module.Gank.adapter.ListViewMainAdapter
import com.apanda.base.Module.Gank.adapter.ViewPagerAdapter
import com.apanda.base.Module.Gank.bean.DayBean
import com.apanda.base.Module.Gank.contract.GankContract
import com.apanda.base.Module.Gank.model.GankModel
import com.apanda.base.Module.Gank.presenter.GankPresenter
import com.apanda.base.R
import com.apanda.base.Utils.logger.L
import com.apanda.base.base.BaseActivity
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.Types.BoomType
import com.nightonke.boommenu.Types.ButtonType
import com.nightonke.boommenu.Types.PlaceType
import com.nightonke.boommenu.Util

class MainActivity : BaseActivity<GankPresenter, GankModel>(), GankContract.View, NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    internal var _Toolbar: Toolbar? = null
    @Bind(R.id.vp_title)
    internal var _VpTitle: ViewPager? = null
    @Bind(R.id.lv_main)
    internal var _LvMain: ListView? = null
    @Bind(R.id.boom)
    internal var _Boom: BoomMenuButton? = null
    @Bind(R.id.nav_view)
    internal var _NavView: NavigationView? = null
    @Bind(R.id.drawer_layout)
    internal var _DrawerLayout: DrawerLayout? = null


    override fun refresh(msg: Message) {

    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initPresenter() {
        mPresenter!!.setVM(this, mModel)
    }

    override fun initView() {

        // mPresenter.lodeGankBeanRequest();
        initToolBar(_Toolbar as Toolbar, "每日精选妹纸", R.color.red_btn_bg_color)
        initBoomButton()
        initDrawer()
    }

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, _DrawerLayout, _Toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        _DrawerLayout!!.setDrawerListener(toggle)
        toggle.syncState()


        _NavView!!.setNavigationItemSelectedListener(this)
    }

    private fun initBoomButton() {
        _Boom!!.setCancelable(true)
        _Boom!!.setOnSubButtonClickListener { buttonIndex ->
            when (buttonIndex) {
                0 -> startActivity(HuabanGirlActivity::class.java)
                1 -> startActivity(HistoryTodayActivity::class.java)
                2 -> startActivity(BeautyActivity::class.java)
            }
        }
    }

    private var init = false

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)


        if (init) return
        init = true

        val subButtonDrawables = arrayOfNulls<Drawable>(3)
        val drawablesResource = intArrayOf(R.mipmap.huagirl, R.mipmap.histotry, R.mipmap.beauty)
        for (i in 0..2)
            subButtonDrawables[i] = ContextCompat.getDrawable(this, drawablesResource[i])

        val subButtonTexts = arrayOf("BoomMenuButton", "View source code", "Follow me")

        val subButtonColors = Array(3) { IntArray(2) }
        for (i in 0..2) {
            subButtonColors[i][1] = ContextCompat.getColor(this, R.color.colorPrimary)
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1])
        }

        _Boom!!.init(
                subButtonDrawables, // The drawables of images of sub buttons. Can not be null.
                null, // The texts of sub buttons, ok to be null.
                subButtonColors, // The colors of sub buttons, including pressed-state and normal-state.
                ButtonType.CIRCLE, // The button type.
                //                ButtonType.HAM,     // The button type.
                BoomType.PARABOLA, // The boom type.
                PlaceType.CIRCLE_3_3, // The place type.
                null, // Ease type to move the sub buttons when showing.
                null, // Ease type to scale the sub buttons when showing.
                null, // Ease type to rotate the sub buttons when showing.
                null, // Ease type to move the sub buttons when dismissing.
                null, // Ease type to scale the sub buttons when dismissing.
                null, // Ease type to rotate the sub buttons when dismissing.
                null                // Rotation degree.
        )

        _Boom!!.setTextViewColor(ContextCompat.getColor(this, R.color.black))

    }


    private fun initViewPager(bitmapBeans: GankBean) {
        val viewPagerAdapter = ViewPagerAdapter(this, bitmapBeans.results!!)
        _VpTitle!!.adapter = viewPagerAdapter
        _VpTitle!!.currentItem = 0
        stopProgressDialog()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            HistoryListActivity.startActivity(this@MainActivity, "福利", "休息视频", 20, 1)

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        if (id == R.id.one) {
            //            setIntent("Android");
            HistoryListActivity.startActivity(this@MainActivity, "福利", "Android", 20, 0)
        } else if (id == R.id.two) {
            //            setIntent("IOS");
            HistoryListActivity.startActivity(this@MainActivity, "福利", "IOS", 20, 0)
        } else if (id == R.id.three) {
            //            setIntent("休息视频");
            HistoryListActivity.startActivity(this@MainActivity, "福利", "休息视频", 20, 0)
        } else if (id == R.id.four) {
            //            setIntent("福利");
            HistoryListActivity.startActivity(this@MainActivity, "福利", "福利", 20, 0)
        } else if (id == R.id.five) {
            //            setIntent("拓展资源");
            HistoryListActivity.startActivity(this@MainActivity, "福利", "拓展资源", 20, 0)
        } else if (id == R.id.six) {
            //            setIntent("前端");
            HistoryListActivity.startActivity(this@MainActivity, "福利", "前端", 20, 0)
        } else if (id == R.id.seven) {
            //            setIntent("App");
            HistoryListActivity.startActivity(this@MainActivity, "福利", "App", 20, 0)
        } else if (id == R.id.eight) {
            //            setIntent("瞎推荐");
            HistoryListActivity.startActivity(this@MainActivity, "福利", "瞎推荐", 20, 0)
        } else if (id == R.id.nine) {
            //            setIntent("随便看看");
            //            HistoryListActivity.startActivity(MainActivity.this, "福利", "随便看看", 20, 0);
        } else if (id == R.id.nav_setting) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    override fun returnGankBean(_gankBean: GankBean) {
        initViewPager(_gankBean)
    }

    override fun returnHistory(History: String) {
        L.json(History)
        mPresenter!!.loadDayBean(History)
    }

    override fun returnDayBean(_dayBean: DayBean) {
        _LvMain!!.adapter = ListViewMainAdapter(this, _dayBean, false)
    }

    override fun showLoading(title: String) {

    }

    override fun stopLoading() {

    }

    override fun showErrorTip(msg: String) {

    }
}
