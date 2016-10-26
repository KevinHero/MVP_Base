package com.apanda.base.Module.Gank.activity

import android.os.Message
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar

import com.apanda.base.Module.Gank.fragment.FragmentGirls
import com.apanda.base.Module.Gank.fragment.ItemFragment
import com.apanda.base.Module.Gank.fragment.TodayFragment
import com.apanda.base.R
import com.apanda.base.Utils.TabHostUtils
import com.apanda.base.base.BaseActivity
import com.apanda.base.base.BaseFragmentAdapter

import java.util.ArrayList

import butterknife.Bind

class HomeActivity : BaseActivity<*, *>() {
    //public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View, TodayFragment.OnFragmentInteractionListener {


    @Bind(R.id.toolbar)
    internal var _Toolbar: Toolbar? = null
    @Bind(R.id.tabs)
    internal var _Tabs: TabLayout? = null
    @Bind(R.id.view_pager)
    internal var _ViewPager: ViewPager? = null

    internal var title = arrayOf("今日", "安卓", "IOS", "福利", "随机", "视频")

    override fun refresh(msg: Message) {

    }

    override val layoutId: Int
        get() = R.layout.activity_gank

    override fun initPresenter() {

    }

    internal var _fragments: MutableList<Fragment> = ArrayList()

    override fun initView() {
        initToolBar(_Toolbar, "干活")
        _fragments.add(TodayFragment.newInstance("今日", ""))
        _fragments.add(ItemFragment.newInstance("Android", ""))
        _fragments.add(ItemFragment.newInstance("iOS", ""))
        _fragments.add(FragmentGirls())
        _fragments.add(ItemFragment.newInstance("all", ""))
        _fragments.add(ItemFragment.newInstance("休息视频", ""))
        val fragmentAdapter = BaseFragmentAdapter(supportFragmentManager, _fragments, title)
        _ViewPager!!.adapter = fragmentAdapter
        _ViewPager!!.offscreenPageLimit = 2
        _Tabs!!.setupWithViewPager(_ViewPager)
        TabHostUtils.dynamicSetTabLayoutMode(_Tabs)
        setPageChangeListener()
    }

    private fun setPageChangeListener() {
        _ViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

}
