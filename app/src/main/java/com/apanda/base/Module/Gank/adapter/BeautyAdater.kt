package com.apanda.base.Module.Gank.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.apanda.base.Module.Gank.bean.NewListBean
import com.apanda.base.R
import com.apanda.base.Widget.WebviewActivity
import com.bumptech.glide.Glide
import java.util.*

/**
 * Created by kai.xiong on 2016/3/27.
 */
class BeautyAdater(internal var mContext: Context, list: List<NewListBean>) : BaseAdapter() {

    internal var list: List<NewListBean> = ArrayList()

    init {
        this.list = list
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): NewListBean {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView

        convertView = View.inflate(mContext, R.layout.beautygirls_cardview, null)

        val viewHolder = ViewHolder(convertView)

        val todayBean = getItem(position)

        viewHolder.tv_title.text = todayBean.title


        if (todayBean.picUrl == null) {
            //                viewHolder.iv_title.setVisibility(View.INVISIBLE);
            Glide.with(mContext).load("http://ww1.sinaimg.cn/mw690/67eaffd3gw1e9mqvlkcl9g2046046mz9.gif").centerCrop().into(viewHolder.iv_title)
        } else {
            Glide.with(mContext).load(todayBean.picUrl).centerCrop().into(viewHolder.iv_title)

        }


        viewHolder.tv_title_desc.text = todayBean.description
        viewHolder.tv_time_beauty.text = todayBean.ctime


        convertView.setOnClickListener { WebviewActivity.startActivity(mContext, todayBean.url.toString(), todayBean.title.toString()) }


        return convertView
    }


    class ViewHolder(var rootView: View) {
        var tv_title: TextView
        var tv_title_desc: TextView
        var iv_title: ImageView
        var tv_time_beauty: TextView

        init {
            this.tv_title = rootView.findViewById(R.id.tv_title) as TextView
            this.tv_title_desc = rootView.findViewById(R.id.tv_title_desc) as TextView
            this.iv_title = rootView.findViewById(R.id.iv_title) as ImageView
            this.tv_time_beauty = rootView.findViewById(R.id.tv_time_beauty) as TextView
        }

    }
}
