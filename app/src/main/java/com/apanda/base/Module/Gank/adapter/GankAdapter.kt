package com.apanda.base.Module.Gank.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.apanda.base.Entity.GankBean
import com.apanda.base.R
import com.apanda.base.Utils.PicassoImageLoader
import com.apanda.base.Widget.WebviewActivity

/**
 * Created by apanda on 2016/10/21.
 */

class GankAdapter : BaseAdapter {

    private val _gankBean: GankBean


    constructor(context: Context, _gankBean: GankBean) {
        this._gankBean = _gankBean

    }

    constructor(_gankBean: GankBean) {
        this._gankBean = _gankBean

    }

    override fun getCount(): Int {
        return _gankBean.results!!.size
    }

    override fun getItem(_i: Int): GankBean.ResultsBean {
        return _gankBean.results!![_i]
    }

    override fun getItemId(_i: Int): Long {
        return 0
    }

    override fun getView(_i: Int, _view: View?, _viewGroup: ViewGroup): View {
        var _view = _view
        var v: ViewHolder? = null
        if (_view == null) {
            _view = View.inflate(_viewGroup.context, R.layout.item_gank, null)
            v = ViewHolder(_view)
            _view!!.tag = v
        } else {
            v = _view.tag as ViewHolder
        }

        val results = getItem(_i)

        v._TvTitle!!.text = results.desc
        v._TvSource!!.text = results.source
        v._TvCato!!.text = results.type
        v._TvTime!!.text = results.publishedAt
        v._TvAuth!!.text = results.who
        if (results.images != null && results.images!!.size > 0) {
            v._IvImg!!.visibility = View.VISIBLE
            PicassoImageLoader().onDisplayImage(_viewGroup.context, v._IvImg as ImageView, results.images!![0].toString())
        } else {
            v._IvImg!!.visibility = View.GONE
        }

        _view.setOnClickListener(View.OnClickListener { WebviewActivity.startActivity(_viewGroup.context, results.url.toString(), results.desc.toString()) })
        return _view
    }

    internal class ViewHolder(view: View) {
        @Bind(R.id.tv_title)
        var _TvTitle: TextView? = null
        @Bind(R.id.tv_source)
        var _TvSource: TextView? = null
        @Bind(R.id.tv_cato)
        var _TvCato: TextView? = null
        @Bind(R.id.tv_time)
        var _TvTime: TextView? = null
        @Bind(R.id.tv_auth)
        var _TvAuth: TextView? = null
        @Bind(R.id.iv_img)
        var _IvImg: ImageView? = null

        init {
            ButterKnife.bind(this, view)
        }
    }
}
