package com.apanda.base.Module.Gank.fragment

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import butterknife.Bind
import butterknife.ButterKnife
import com.apanda.base.Module.Gank.adapter.GirlsAdapter
import com.apanda.base.Module.Gank.bean.GilrsBean
import com.apanda.base.Module.Gank.contract.FragmentGirlsContract
import com.apanda.base.Module.Gank.model.FragmentGirlsModel
import com.apanda.base.Module.Gank.presenter.FragmentGirlsPresenter
import com.apanda.base.R
import com.apanda.base.base.BaseFragment


class FragmentGirls : BaseFragment<FragmentGirlsPresenter, FragmentGirlsModel>(), FragmentGirlsContract.View {


    @Bind(R.id.rcyview)
    internal var _Rcyview: RecyclerView? = null

    protected override val layoutResource: Int
        get() = R.layout.content_activity_fragment_girlslayout

    override fun initPresenter() {
        mPresenter!!.setVM(this, mModel)
    }

    public override fun initView() {
        mPresenter!!.loadGrilsBean()
    }

    override fun showLoading(title: String) {
        startProgressDialog()
    }

    override fun stopLoading() {
        stopProgressDialog()
    }

    override fun showErrorTip(msg: String) {
        showErrorTip(msg)
    }

    override fun returnGrilsBean(_gankBean: GilrsBean) {
        val adapter = GirlsAdapter(activity, _gankBean.results!!)
        _Rcyview!!.adapter = adapter
        _Rcyview!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        ButterKnife.unbind(this)
    }
}


