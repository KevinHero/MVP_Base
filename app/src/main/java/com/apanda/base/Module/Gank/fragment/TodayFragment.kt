package com.apanda.base.Module.Gank.fragment

import android.os.Bundle
import android.support.v4.view.ViewPager
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
import com.apanda.base.Widget.NestedListView
import com.apanda.base.base.BaseFragment

class TodayFragment : BaseFragment<GankPresenter, GankModel>(), GankContract.View {

    @Bind(R.id.vp_title)
    internal var _VpTitle: ViewPager? = null
    @Bind(R.id.lv_main)
    internal var _LvMain: NestedListView? = null

    private var mParam1: String? = null
    private var mParam2: String? = null


    protected override val layoutResource: Int
        get() = R.layout.content_main

    override fun initPresenter() {
        mPresenter!!.setVM(this, mModel)
    }

    override fun initView() {
        startProgressDialog()
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
        mPresenter!!.lodeGankBeanRequest()
        mPresenter!!.loadHistoty()
    }


    override fun returnGankBean(_gankBean: GankBean) {
        initViewPager(_gankBean)
    }

    private fun initViewPager(bitmapBeans: GankBean) {
        val viewPagerAdapter = ViewPagerAdapter(activity, bitmapBeans.results!!)
        _VpTitle!!.adapter = viewPagerAdapter
        _VpTitle!!.currentItem = 0
    }

    override fun returnHistory(History: String) {
        L.json(History)
        mPresenter!!.loadDayBean(History)
    }

    override fun returnDayBean(_dayBean: DayBean) {
        _LvMain!!.adapter = ListViewMainAdapter(context, _dayBean, false)
        stopProgressDialog()
    }

    override fun showLoading(title: String) {
        showLoading(title)
    }

    override fun stopLoading() {
        stopLoading()
    }

    override fun showErrorTip(msg: String) {

    }

    companion object {


        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"


        fun newInstance(param1: String, param2: String): TodayFragment {
            val fragment = TodayFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
