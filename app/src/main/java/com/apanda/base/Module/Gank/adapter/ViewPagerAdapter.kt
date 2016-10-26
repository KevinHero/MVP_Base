package com.apanda.base.Module.Gank.adapter

import android.app.Activity
import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.apanda.base.Entity.GankBean
import com.apanda.base.R
import com.apanda.base.Utils.GlideImageLoader
import com.apanda.base.Widget.imagePager.BigImagePagerActivity
import com.bumptech.glide.Glide
import java.util.*

/**
 * Created by kai.xiong on 2016/3/25.
 */
class ViewPagerAdapter : PagerAdapter {

    private val imageViewContainer = ArrayList<ImageView>()
    internal var bitmapBeans: List<GankBean.ResultsBean>? = null
    private var context: Activity? = null
    private val _arrayList = ArrayList<String>()

    constructor(context: Context, imageViewContainer: MutableList<ImageView>) {
        this.imageViewContainer.plus(imageViewContainer)
        this.context = context as Activity
    }

    constructor(context: Activity, bitmapBeans: List<GankBean.ResultsBean>) {
        this.bitmapBeans = bitmapBeans

        for (i in bitmapBeans.indices) {
            _arrayList.plus(bitmapBeans[i].url)
            val imageView = ImageView(context)
            Glide.with(context).load(bitmapBeans[i].url).centerCrop().into(imageView)
            imageViewContainer.add(imageView)
        }

        this.context = context
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(imageViewContainer[position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = View.inflate(context, R.layout.imageview, null)
        val name = view.findViewById(R.id.tv_name) as TextView
        val time = view.findViewById(R.id.tv_time) as TextView
        val iv_pic = view.findViewById(R.id.iv_pic) as ImageView

        val resultsBean = bitmapBeans!![position]
        GlideImageLoader.instance.onDisplayImage(context!!, iv_pic, resultsBean.url.toString())
        name.text = resultsBean.who
        time.text = resultsBean.publishedAt
        // 为每一个page添加点击事件
        view.setOnClickListener { BigImagePagerActivity.startImagePagerActivity(context!!, _arrayList, position) }

        container.addView(view)
        return view
    }


    override fun getCount(): Int {
        return imageViewContainer.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}


//@Override
//public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(imageViewContainer.get(position % imageViewContainer.size()));
//        }
//
//@Override
//public Object instantiateItem(ViewGroup container, int position) {
//        View view = imageViewContainer.get(position % imageViewContainer.size());
//
//        // 为每一个page添加点击事件
//        view.setOnClickListener(new View.OnClickListener() {
//
//@Override
//public void onClick(View v) {
//        Toast.makeText(MainActivity.this, "Page 被点击了", Toast.LENGTH_SHORT).show();
//        }
//
//        });
//
//        container.addView(view);
//        return view;
//        }
//
//@Override
//public int getCount() {
//        return Integer.MAX_VALUE;
//        }
//
//@Override
//public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//        }