package com.apanda.base.Module.Gank.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.apanda.base.Module.Gank.bean.ItemsBean
import com.apanda.base.R
import com.apanda.base.Utils.GlideImageLoader
import com.apanda.base.Widget.WebviewActivity
import java.util.*

/**
 * Created by apanda on 2016/10/24.
 */
class ItemAdapter(private val mContext: Activity, _results: List<ItemsBean.ResultsBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val mLayoutInflater: LayoutInflater

    private val _results = ArrayList<ItemsBean.ResultsBean>()

    init {
        mLayoutInflater = LayoutInflater.from(mContext)
        if (_results.size > 0) {
            this._results.clear()
            this._results.addAll(_results)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(mLayoutInflater.inflate(R.layout.ios_cardview, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as ItemHolder

        val bean = _results[position]
        val images = bean.images
        if (images != null && images.size > 0) {
            itemHolder._IvTitle!!.visibility = View.VISIBLE
            GlideImageLoader.instance.onDisplayImage(mContext, itemHolder._IvTitle as ImageView, images[0])
        } else {
            itemHolder._IvTitle!!.visibility = View.GONE
        }
        itemHolder._TvTitle!!.text = bean.desc
        itemHolder._TvDate!!.text = bean.createdAt!!.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]

    }

    override fun getItemCount(): Int {
        return _results.size
    }

    internal inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        @Bind(R.id.iv_title)
        var _IvTitle: ImageView? = null
        @Bind(R.id.tv_title)
        var _TvTitle: TextView? = null
        @Bind(R.id.tv_date)
        var _TvDate: TextView? = null

        init {
            ButterKnife.bind(this, view)
            view.setOnClickListener {
                val resultsBean = _results[position]
                WebviewActivity.startActivity(mContext, resultsBean.url.toString(), resultsBean.desc.toString())
            }
        }
    }
}
