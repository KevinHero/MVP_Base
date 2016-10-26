package com.apanda.base.Module.Gank.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.apanda.base.Module.Gank.bean.ItemBean
import com.apanda.base.R
import com.apanda.base.Widget.WebviewActivity

/**
 * Created by kai.xiong on 2016/3/26.
 */
class ListViewAdapter(private val mContext: Context, internal var dayBean:

List<ItemBean>) : BaseAdapter() {

    override fun getCount(): Int {
        return dayBean.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView

        val itemBeans = dayBean[position]

        convertView = View.inflate(mContext, R.layout.android_item, null)
        val textView = convertView.findViewById(R.id.tv_item_tips) as TextView
        val tv_read_status = convertView.findViewById(R.id.tv_read_status) as TextView
        textView.text = itemBeans.desc
        convertView.setOnClickListener {
            val title = itemBeans.type + "-" + itemBeans.desc
            WebviewActivity.startActivity(mContext, itemBeans.url!!, title)
            tv_read_status.visibility = View.VISIBLE
        }

        return convertView
    }
}
