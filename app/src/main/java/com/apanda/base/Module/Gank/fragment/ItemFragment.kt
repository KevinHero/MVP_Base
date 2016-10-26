package com.apanda.base.Module.Gank.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import butterknife.Bind
import butterknife.ButterKnife
import com.apanda.base.Module.Gank.adapter.ItemAdapter
import com.apanda.base.Module.Gank.bean.ItemsBean
import com.apanda.base.Module.Gank.contract.ItemContract
import com.apanda.base.Module.Gank.model.ItemModel
import com.apanda.base.Module.Gank.presenter.ItemPresenter
import com.apanda.base.R
import com.apanda.base.base.BaseFragment

/**
 * Created by apanda on 2016/10/23.
 */

class ItemFragment : BaseFragment<ItemPresenter, ItemModel>(), ItemContract.View {

    @Bind(R.id.rcyview)
    internal var _Rcyview: RecyclerView? = null

    protected override val layoutResource: Int
        get() = R.layout.fragment_today

    override fun initPresenter() {
        mPresenter!!.setVM(this, mModel)
    }

    private var mParam1: String? = null
    private var mParam2: String? = null


    override fun initView() {
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }

        mPresenter!!.loadBean(mParam1!!)
        _Rcyview!!.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
    }


    override fun returnItemsBean(_gankBean: ItemsBean) {
        _Rcyview!!.adapter = ItemAdapter(activity, _gankBean.results!!)
    }

    override fun showLoading(title: String) {

    }

    override fun stopLoading() {

    }

    override fun showErrorTip(msg: String) {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        ButterKnife.unbind(this)
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): ItemFragment {
            val fragment = ItemFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
