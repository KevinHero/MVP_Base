package com.apanda.base.Module.Gank.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.apanda.base.Module.Gank.bean.DayBean
import com.apanda.base.R

/**
 * Created by kai.xiong on 2016/3/25.
 */
class Adapter(internal var dayBean:

              DayBean) : RecyclerView.Adapter<Adapter.NormalTextViewHolder>() {
    internal var list: MutableList<*> ?= null

    init {
//
//        if (dayBean.results!!.Android != null) list.plus(dayBean.results!!.Android)
//
//        if (dayBean.results!!.App != null) list.plus(dayBean.results!!.App)
//
//        if (dayBean.results!!.iOS != null) list.plus(dayBean.results!!.iOS)
//
//        if (dayBean.results!!.休息视频 != null) list.plus(dayBean.results!!.休息视频)
//
//        if (dayBean.results!!.前端 != null) list.plus(dayBean.results!!.前端)
//
//        if (dayBean.results!!.拓展资源 != null) list.plus(dayBean.results!!.拓展资源)
//
//        if (dayBean.results!!.瞎推荐 != null) list.plus(dayBean.results!!.瞎推荐)
//
//        if (dayBean.results!!.福利 != null) list.plus(dayBean.results!!.福利)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalTextViewHolder? {
        return null//new NormalTextViewHolder(View.inflate(MainActivity.this, R.layout.listview_cardview, null));
    }

    override fun onBindViewHolder(holder: NormalTextViewHolder, position: Int) {

     //   val itemBean = list?[position] as List<ItemBean>


    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    inner class NormalTextViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {


        init {


            val imageView = view.findViewById(R.id.iv_title) as ImageView

            //                Glide.with(MainActivity.this).load(mDayBean.results.福利.get(0).url).centerCrop().into(imageView);
            view.setOnClickListener { }
        }
    }
}

