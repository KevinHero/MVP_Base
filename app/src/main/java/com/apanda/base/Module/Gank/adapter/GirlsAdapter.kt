package com.apanda.base.Module.Gank.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.apanda.base.Module.Gank.bean.GilrsBean
import com.apanda.base.R
import com.apanda.base.Utils.GlideImageLoader
import com.apanda.base.Widget.imagePager.BigImagePagerActivity
import java.util.*

/**
 * Created by apanda on 2016/10/24.
 */
class GirlsAdapter(_context: Context, _results: List<GilrsBean.ResultsBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val mLayoutInflater: LayoutInflater
    private val mContext: Activity
    internal var _results: MutableList<GilrsBean.ResultsBean> = ArrayList()

    init {
        mContext = _context as Activity
        mLayoutInflater = LayoutInflater.from(_context)
        //  _results.clear();
        this._results.addAll(_results)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageHolder(mLayoutInflater.inflate(R.layout.imageview, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val iHolder = holder as ImageHolder
        GlideImageLoader.instance.onDisplayImage(mContext, iHolder._IvPic as ImageView, _results[position].url.toString())
        iHolder._IvPic!!.setOnClickListener {
            val strings = ArrayList<String>()
            strings.add(_results[position].url.toString())
            BigImagePagerActivity.startImagePagerActivity(mContext, strings, 0)
        }
    }

    override fun getItemCount(): Int {
        return _results.size
    }

    internal class ImageHolder(_inflate: View) : RecyclerView.ViewHolder(_inflate) {
        @Bind(R.id.iv_pic)
        var _IvPic: ImageView? = null
        @Bind(R.id.tv_name)
        var _TvName: TextView? = null
        @Bind(R.id.tv_time)
        var _TvTime: TextView? = null

        init {
            ButterKnife.bind(this, _inflate)
        }
    }

}
