package com.apanda.base.Module.Gank.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.apanda.base.Module.Gank.bean.DayBean
import com.apanda.base.R
import com.apanda.base.Widget.NestedListView

/**
 * Created by kai.xiong on 2016/3/26.
 */
class ListViewMainAdapter(private val mContext: Context, internal var dayBean:


DayBean, var isHis: Boolean) : BaseAdapter() {

    //var list: MutableList ? = null!!
//    init {
//
//
//        if (isHis) {
//            if (dayBean.results!!.福利 != null) list.plus(dayBean.results!!.福利)
//        }
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




    override fun getCount(): Int {
        return 0
//        return list.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView

      //  val itemBeans = list?.get(position) as List<ItemBean>


        convertView = View.inflate(mContext, R.layout.android_cardview, null)

        val nestedListView = convertView.findViewById(R.id.nestedlistview) as NestedListView
        val textView = convertView.findViewById(R.id.tv_android_carview) as TextView
        val tv_name_main = convertView.findViewById(R.id.tv_name_main) as TextView
        val tv_time_main = convertView.findViewById(R.id.tv_time_main) as TextView
        val rl_main = convertView.findViewById(R.id.rl_main) as RelativeLayout
        val iv_title_main = convertView.findViewById(R.id.iv_title_main) as ImageView

//
//        if (itemBeans[0].type == "福利") {
//            nestedListView.visibility = View.GONE
//            rl_main.visibility = View.VISIBLE
//            Glide.with(mContext).load(itemBeans[0].url).centerCrop().into(iv_title_main)
//            tv_time_main.text = itemBeans[0].createdAt!!.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
//            tv_name_main.text = itemBeans[0].who
//
//        } else {
//            rl_main.visibility = View.GONE
//            nestedListView.adapter = ListViewAdapter(mContext, itemBeans)
//        }
//        textView.text = itemBeans[0].type



        return convertView

    }
}
