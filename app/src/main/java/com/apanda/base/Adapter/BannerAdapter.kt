package com.apanda.base.Adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 包名:
 * 作者: created by 熊凯 on 2016/9/29 13:37
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: BannerAdapter
 */

class BannerAdapter(private val imageViewContainer: List<View>) : PagerAdapter() {

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(imageViewContainer[position % imageViewContainer.size])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = imageViewContainer[position % imageViewContainer.size]
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }


}
